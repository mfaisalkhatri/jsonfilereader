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
import com.testtech.jsonfilereader.testdatagetter.TestDataProductMaster;

public class ProductMasterTests {

	String key = "product";

	@Test(dataProvider = "ProductAddData")
	public void addProduct(TestDataProductMaster prdctData) {

		System.out.println(prdctData.getProductcode());
		System.out.println(prdctData.getProductname());
		System.out.println(prdctData.getDescription());
		System.out.println(prdctData.getMarketprice());
		System.out.println(prdctData.getChannel()[0] + " " + prdctData.getChannel()[1]);
		System.out.println(prdctData.getBatchmapping().getRegno());
		System.out.println(prdctData.getBatchmapping().getPatentno());

	}

	@DataProvider(name = "ProductAddData")
	private Object[][] productData() throws FileNotFoundException {

		String fileName = "productdata.json";
		String filePath = getClass().getClassLoader().getResource(fileName).getPath();

		JsonElement jsonData = new JsonParser().parse(new FileReader(filePath));
		JsonElement prdctData = jsonData.getAsJsonObject().get(key);
		List<TestDataProductMaster> testData = new Gson().fromJson(prdctData,
				new TypeToken<List<TestDataProductMaster>>() {
				}.getType());
		Object[][] returnValue = new Object[testData.size()][1];
		int index = 0;
		for (Object[] each : returnValue) {
			each[0] = testData.get(index++);

		}
		return returnValue;
	}

}
