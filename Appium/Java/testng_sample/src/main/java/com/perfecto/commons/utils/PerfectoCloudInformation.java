package com.perfecto.commons.utils;

import java.util.Properties;

public class PerfectoCloudInformation {

	private static PerfectoCloudInformation perfectoConst;
	private Properties cloudProperties;
	private String availableDeviceUrl;
	private String listDeviceUrl;
	private String hubUrl;

	private PerfectoCloudInformation() {

		SuiteConstants suiteConst = new SuiteConstants();
		suiteConst.loadPropertiesFile("src/test/resources/perfecto/cloud.properties");
		cloudProperties = suiteConst.getProperties();
		
		this.hubUrl = String.format("https://%s.perfectomobile.com/nexperience/perfectomobile/wd/hub",getConstant("perfecto.cloudname"));

		availableDeviceUrl = String.format(
				"https://%s.perfectomobile.com/services/handsets?operation=list&user=%s&password=%s&inUse=false&status=connected",
				getConstant("perfecto.cloudname"), getConstant("perfecto.username"), getConstant("perfecto.apikey"));
		
		listDeviceUrl = String.format(
				"https://%s.perfectomobile.com/services/handsets?operation=list&user=%s&password=%s&status=connected",
				getConstant("perfecto.cloudname"), getConstant("perfecto.username"), getConstant("perfecto.apikey"));
	}
	
	public String getAvailableDevicesAPIBaseUrl() {
		return this.availableDeviceUrl;
	}
	
	public String getDevicesAPIBaseUrl() {
		return this.listDeviceUrl;
	}

	public static PerfectoCloudInformation loadPerfectoConstants() {
		if (perfectoConst == null) {
			perfectoConst = new PerfectoCloudInformation();
		}

		return perfectoConst;
	}

	public String getConstant(String constantName) {
		return this.cloudProperties.getProperty(constantName, "");
	}

	public String getHubUrl() {
		return hubUrl;
	}
	
}
