package com.perfecto.reporting;

import com.perfecto.commons.utils.ReportiumConstant;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class Reports {
	
	private PerfectoExecutionContext execContext;
	private ReportiumClient reportiumClient;

	public Reports(AppiumDriver<MobileElement> driver) {
		ReportiumConstant reportConstants = ReportiumConstant.getReportiumConstant();
		
		Project project = new Project(reportConstants.getConstant("projectname"),reportConstants.getConstant("projectversion"));
		Job job = new Job(reportConstants.getConstant("jobname"),Integer.parseInt(reportConstants.getConstant("jobversion")));
		
		this.execContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withProject(project)
				.withJob(job)
				.withWebDriver(driver)
				.build();
		
		this.reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(execContext);
	}
	
	public void startTest(String testName) {
		TestContext testContext = new TestContext.Builder().build();
		this.reportiumClient.testStart(testName, testContext);
	}
	
	public void endTest(boolean isPass,String... errorMessage) {
		
		if(isPass) {
			this.reportiumClient.testStop(TestResultFactory.createSuccess());
		}else {
			if(errorMessage.length>0) {
				this.reportiumClient.testStop(TestResultFactory.createFailure(errorMessage[0]));
			}else {
				this.reportiumClient.testStop(TestResultFactory.createFailure("No Failure Reason provided"));
			}
		}
	}
	
	public void startStep(String stepDescription) {
		this.reportiumClient.stepStart(stepDescription);
	}
	
	public void endStep() {
		this.reportiumClient.stepEnd();
	}
	
}
