package com.perfecto.reporting.sample;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import static com.perfecto.reporting.sample.TodoMvcWithListenerTest.IS_LOCAL_DRIVER;

/**
 * TestNG listener using custom credentials for connecting to Reportium
 */
public class CustomReportingTestListener extends ReportiumTestNgListener {

    @Override
    protected WebDriver getWebDriver(ITestResult testResult) {
       return null; // take WebDriver from somewhere
    }
}
