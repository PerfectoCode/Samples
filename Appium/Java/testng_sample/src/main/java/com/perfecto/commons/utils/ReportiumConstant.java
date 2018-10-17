package com.perfecto.commons.utils;

import java.util.Properties;

public class ReportiumConstant {
	
	private static ReportiumConstant reportiumConst;
	private Properties reportiumProp
	;

	private ReportiumConstant() {
		SuiteConstants suiteConst = new SuiteConstants();
		suiteConst.loadPropertiesFile("src/test/resources/perfecto/reportium.properties");
		reportiumProp = suiteConst.getProperties();

	}
	
	public static ReportiumConstant getReportiumConstant() {
		if(reportiumConst==null) {
			reportiumConst = new ReportiumConstant();
		}
		
		return reportiumConst;
	}
	
	public String getConstant(String constantName) {
		return this.reportiumProp.getProperty(constantName, "");
	}
	
	
	
}
