**This is a sample example project created for reading the data from json file.**

*An easy to use utility for reading json file which can directly be used in your tasks.*

**Getting Started**

This wiki includes all the information you need to get started including setup, usage, advantages, sample test.

**What to do when you need help?**
* Discuss your queries by writing to us on our mailing list
* If you find any issue which is bottleneck for you, search the issue tracker to see if it is already raised.
* If not raised, then you can create a new issue with required details as mentioned in the issue template.

**What you do if you like the project?**
* Spread the word with your network.
* Star the project to make the project popular.
* Stay updated with the project progress by Watching it.
* Contribute to fix open issues, documentations or add new features. To know more, see our contributing page.

**Examples and How to Use**

**This project highlights the following problems I encountered while reading json file using java:**

A. Reading the data from the array of json file. 
B. Reading array within array from the json file.

**These examples have been developed using Java, Gson dependency to read json file.**
**The following are the steps I have followed for creating this example project:**

A. Json File - Reading the data from the array of json file. 
The following is the json file :
```
{
	"org": [
		{
			"email": "f128@yahu.com",
			"password": "123456",
			"orgname": "CStockLtd",
			"displayname": "C Stock Broker Ltd",
			"orgtype": "Organisation"
		},
		{
			"email": "f127@yahu.com",
			"password": "123456",
			"orgname": "IStockLtd",
			"displayname": "I Stock Broker Ltd",
			"orgtype": "Organisation"
		}
	]
}
```
**Here, "org" is an array which has multiple fields. If we need to read the values using testng data provider, following is the step:**

01. First create a getter class for all the fields in the file:  
E.g.  
```
public class JsonFileDataGetterSetter {
	private String email;
	
	public String getEmail() {
		return email;
	}
}
```
02. Following DataProvider Method(Testng) can be used to call the field and get their value in the test:  
```
@DataProvider(name = "JsonData")
	public Object[][] publisherData() throws FileNotFoundException {
		String fileName = "JsonDataFile.json"; -- Mention the json File name here.
		String filePath = getClass().getClassLoader().getResource(fileName).getPath();

		JsonElement jsonData = new JsonParser().parse(new FileReader(filePath));
		JsonElement orgData = jsonData.getAsJsonObject().get("org"); --- Mention the array name which you want to parse.
		List<JsonFileDataGetterSetter> testData = new Gson().fromJson(orgData,
				new TypeToken<List<JsonFileDataGetterSetter>>() {
				}.getType());
		Object[][] returnValue = new Object[testData.size()][1];
		int index = 0;
		for (Object[] each : returnValue) {
			each[0] = testData.get(index++);
}
```
03.  In your @test method call the gettermethod class and the dataprovider defined above:  
E.g.  
```
@Test(dataProvider = "JsonData")
	public void publisherOnboardingTest(JsonFileDataGetterSetter orgData) throws FileNotFoundException {
As
		System.out.println("Email Id is: " + orgData.getEmail());
}
```
04. Run the test and you will get the email value from the json file in the output. Remember, as "Org" is an array, it will iterate through all the fields named "email" in the file and will print the same in the output.


**B. Likewise, in case if there is array within array in the json file.**
E.g. Json file - (Check "Channel" and "batchmapping" fields in the below snippet)
```
{
"product": 	[
{
"productcode": "P55225",
"productname": "Profit Calls",
"description": "New product to publish equity market calls.",
"marketprice": "299",
"channel": ["email","pushnotification"],
"batchmapping" : {"regno": "123456","patentno": "N-1214"}
}]
}
```
01. For array having new fields- While creating the gettersetter initiliaze a new class for the array fields:  
02. For array with values only, using String array[] to get values from the file.  
E.g.  
```
public class TestDataProductMaster {
	private String channel[];
	private BatchMappingData batchmapping;

		public String[] getChannel() {
		return channel;
}
public BatchMappingData getBatchmapping() {
		return batchmapping;	
}
}
```
And this is how the new class "BatchMappingData" will be implemented:
```
public class BatchMappingData {
	private String regno;
	private String patentno;
	
	public String getRegno() {
		return regno;
}

	public String getPatentno() {
		return patentno;
	}	
}
```
03. When You write the tests, use as per following steps:
```
public class ProductMasterTests {

	String key = "product";

	@Test(dataProvider = "ProductAddData")
	public void addProduct(TestDataProductMaster prdctData) {

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
```