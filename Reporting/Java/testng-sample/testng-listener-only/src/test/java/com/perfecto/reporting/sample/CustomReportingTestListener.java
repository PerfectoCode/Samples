package com.perfecto.reporting.sample;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.testng.ReportiumTestNgListener;
import org.testng.ITestResult;

import static com.perfecto.reporting.sample.TodoMvcWithListenerTest.IS_LOCAL_DRIVER;

/**
 * TestNG listener using custom credentials for connecting to Reportium
 */
public class CustomReportingTestListener extends ReportiumTestNgListener {

    @Override
    protected ReportiumClient createReportiumClient(ITestResult testResult) {
        if (!Boolean.parseBoolean(System.getProperty(IS_LOCAL_DRIVER))) {
            return super.createReportiumClient(testResult);
        }
        else {
            return new ReportiumClientFactory().createLoggerClient();
        }
    }
}
