package com.perfecto.commons.testng;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.perfecto.commons.testng.Logger.STATUS;
import com.perfecto.commons.utils.DevicesToTestOn.Device;
import com.perfecto.commons.utils.PerfectoCloudInformation;
import com.perfecto.mobile.android.CustomAndroidDriver;
import com.perfecto.mobile.ios.CustomIOSDriver;
import com.perfecto.mobile.utils.DesiredCapability;
import com.perfecto.mobile.utils.DeviceUtils;
import com.perfecto.reporting.Reports;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TestNGListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

	public void onTestStart(ITestResult result) {

		TestCases testCase = (TestCases) result.getInstance();
		testCase.getReport().startTest(result.getMethod().getDescription());
		
		Logger.log(STATUS.INFO, "================================ Test Execution Started - "
				+ result.getMethod().getDescription() + " ================================");

	}

	public void onTestSuccess(ITestResult result) {
		
		TestCases testCase = (TestCases) result.getInstance();
		testCase.getReport().endTest(true);
		
		Logger.log(STATUS.INFO, "================================ [PASS] Test Execution Completed - "
				+ result.getMethod().getDescription() + " ================================");

	}

	public void onTestFailure(ITestResult result) {

		TestCases testCase = (TestCases) result.getInstance();
		
		if(testCase.getReport()!=null) {
			testCase.getReport().endTest(false, result.getThrowable().getMessage());
		}
		
		Logger.log(STATUS.INFO, "================================ [FAIL] Test Execution Failed - "
				+ result.getMethod().getDescription() + " ================================");

	}

	public void onTestSkipped(ITestResult result) {

		TestCases testCase = (TestCases) result.getInstance();
		if(testCase.getReport()!=null) {
			testCase.getReport().endTest(false, result.getThrowable().getMessage());
		}
		
		Logger.log(STATUS.INFO, "================================ [FAIL] Test Execution Failed - "
				+ result.getMethod().getDescription() + " ================================");

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	public void onStart(ITestContext context) {
	}

	public void onFinish(ITestContext context) {

	}

	@Override
	public void onStart(ISuite suite) {
		Logger.log(STATUS.INFO,
				"================================ Test Suite Execution Started ================================");

	}

	@Override
	public void onFinish(ISuite suite) {
		Logger.log(STATUS.INFO,
				"================================ Test Suite Execution Completed ================================");

	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

		if (method.getTestMethod().isBeforeMethodConfiguration()) {

			TestCases testCase = (TestCases) testResult.getInstance();
			TestCaseInputs inputs = testCase.getTestCaseInputs();
			Device deviceInformation = inputs.getDeviceInformation();
			try {
				testCase.setDeviceAllocated(DeviceUtils.isDeviceAvailable(deviceInformation));

				DesiredCapabilities desiredCapabilities = DesiredCapability.getDesiredCapabilities(deviceInformation);

				try {

					AppiumDriver<MobileElement> webDriver;

					PerfectoCloudInformation cloudInformation = PerfectoCloudInformation.loadPerfectoConstants();
					
					if ("Android".equalsIgnoreCase((String) desiredCapabilities.getCapability("platformName"))) {
						webDriver = new CustomAndroidDriver(new URL(cloudInformation.getHubUrl()), desiredCapabilities);
					} else {
						webDriver = new CustomIOSDriver(new URL(cloudInformation.getHubUrl()), desiredCapabilities);
					}

					Reports report = new Reports(webDriver);
					testCase.setDriver(webDriver);
					testCase.setReportiumClient(report);

				} catch (MalformedURLException e) {
					Logger.log(STATUS.FAIL, e.getLocalizedMessage());
				} catch (WebDriverException e) {
					Logger.log(STATUS.FAIL, e.getLocalizedMessage());
				}

			} catch (TimeoutException e) {
				Logger.log(STATUS.FAIL, e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

		if (method.getTestMethod().isAfterMethodConfiguration()) {
			
			TestCases testCase = (TestCases) testResult.getInstance();			
			testCase.getDriver().close();
			testCase.getDriver().quit();
		}

	}

}
