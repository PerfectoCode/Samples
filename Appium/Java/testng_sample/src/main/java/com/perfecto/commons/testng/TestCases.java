package com.perfecto.commons.testng;

import org.testng.xml.XmlTest;

import com.perfecto.reporting.Reports;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TestCases extends XmlTest{

	private static final long serialVersionUID = 1L;
	
	private Reports reportium;
	private TestCaseInputs inputs;
	private boolean deviceAllocated = false;
	private AppiumDriver<MobileElement> driver;
	private boolean closeStep = false;
	
	
	public TestCases(TestCaseInputs inputs) {
		this.inputs = inputs;
	}
	
	public TestCaseInputs getTestCaseInputs() {
		return this.inputs;
	}
	
	public void setReportiumClient(Reports reportium) {
		this.reportium = reportium;
	}
	
	public void StartStep(String stepDescription) {
		this.reportium.startStep(stepDescription);
	}
	
	public void EndStep() {
		this.reportium.endStep();
	}

	public boolean isDeviceAllocated() {
		return deviceAllocated;
	}

	public void setDeviceAllocated(boolean deviceAllocated) {
		this.deviceAllocated = deviceAllocated;
	}

	public AppiumDriver<MobileElement> getDriver() {
		return driver;
	}

	public void setDriver(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
	}
	
	public Reports getReport() {
		return this.reportium;
	}
	
	public boolean closeStep() {
		return this.closeStep;
	}
	
	public void setCloseStep(boolean closeStep) {
		this.closeStep = closeStep;
	}
	
}
