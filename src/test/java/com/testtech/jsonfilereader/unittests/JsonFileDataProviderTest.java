package com.testtech.jsonfilereader.unittests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.testtech.jsonfilereader.testdatagetter.JsonFileDataGetterSetter;

public class JsonFileDataProviderTest {

	@Test(dataProvider = "JsonData")
	public void publisherOnboardingTest(JsonFileDataGetterSetter orgData) throws FileNotFoundException {

		System.out.println("Email Id is: " + orgData.getEmail());
		System.out.println("Password is: " + orgData.getPassword());
		System.out.println("Org Name is: " + orgData.getOrgname());
		System.out.println("Display Name is: " + orgData.getDisplayname());
		System.out.println("Org Type is: " + orgData.getOrgtype());
		System.out.println();

	}

	@DataProvider(name = "JsonData")
	public Object[][] publisherData() throws FileNotFoundException {
		String fileName = "JsonDataFile.json";
		String filePath = getClass().getClassLoader().getResource(fileName).getPath();

		JsonElement jsonData = new JsonParser().parse(new FileReader(filePath));
		JsonElement orgData = jsonData.getAsJsonObject().get("org");
		List<JsonFileDataGetterSetter> testData = new Gson().fromJson(orgData,
				new TypeToken<List<JsonFileDataGetterSetter>>() {
				}.getType());
		Object[][] returnValue = new Object[testData.size()][1];
		int index = 0;
		for (Object[] each : returnValue) {
			each[0] = testData.get(index++);
		}
		return returnValue;
	}

}
