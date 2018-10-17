package com.perfecto.commons.testng;


import org.json.JSONObject;

import com.perfecto.commons.utils.DevicesToTestOn.Device;

public class TestCaseInputs {
	
	private Device deviceInformation;
	private JSONObject testData;
	private String groupName;
	
	public TestCaseInputs(String groupName, Device deviceInformation, JSONObject testData) {
		this.deviceInformation = deviceInformation;
		this.testData = testData;
		this.groupName = groupName;
	}
	
	public TestCaseInputs(String groupName, Device deviceInformation) {
		this.deviceInformation = deviceInformation;
	}
	
	public TestCaseInputs(String groupName, JSONObject testData) {
		this.testData = testData;
	}
	
	public Device getDeviceInformation() {
		return this.deviceInformation;
	}
	
	public JSONObject getTestData() {
		return this.testData;
	}
	
	public String getGroupName() {
		return groupName;
	}

}