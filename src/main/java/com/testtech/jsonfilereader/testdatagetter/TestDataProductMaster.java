package com.testtech.jsonfilereader.testdatagetter;

public class TestDataProductMaster {

	private String productcode;
	private String productname;
	private String description;
	private String marketprice;
	private String[] channel;
	private BatchMappingData batchmapping;

	public String getProductcode() {
		return productcode;
	}

	public String getProductname() {
		return productname;
	}

	public String getDescription() {
		return description;
	}

	public String getMarketprice() {
		return marketprice;
	}

	public String[] getChannel() {
		return channel;
	}

	public BatchMappingData getBatchmapping() {
		return batchmapping;
	}

}
