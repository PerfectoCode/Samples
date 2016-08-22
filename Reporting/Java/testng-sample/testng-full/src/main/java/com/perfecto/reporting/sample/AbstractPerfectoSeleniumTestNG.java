package com.perfecto.reporting.sample;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.exception.ReportiumException;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Base test for TestNG tests.
 * <p>
 * This class handles the connection to Perfecto reporting and seamlessly reports test start and end to reduce boilerplate code
 * in the actual test cases.
 */
@SuppressWarnings("Duplicates")
public class AbstractPerfectoSeleniumTestNG {

    protected WebDriver driver;
    protected ReportiumClient reportiumClient;

    private static final String SELENIUM_GRID_URL_KEY = "selenium-grid-url";
    private static final String SELENIUM_GRID_USERNAME_KEY = "selenium-grid-username";
    private static final String SELENIUM_GRID_PASSWORD_KEY = "selenium-grid-password";
    private static final String IS_LOCAL_DRIVER = "is-local-driver";


    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass() throws MalformedURLException {
        driver = createDriver();
        reportiumClient = createReportiumClient(driver);
    }

    @AfterClass(alwaysRun = true)
    public void baseAfterClass() {
        System.out.println("Report url = " + reportiumClient.getReportUrl());
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeTest(Method method) {
        String testName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        reportiumClient.testStart(testName, new TestContext());
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult testResult) {
        int status = testResult.getStatus();
        switch (status) {
            case ITestResult.FAILURE:
                reportiumClient.testStop(TestResultFactory.createFailure("An error occurred", testResult.getThrowable()));
                break;
            case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
            case ITestResult.SUCCESS:
                reportiumClient.testStop(TestResultFactory.createSuccess());
                break;
            case ITestResult.SKIP:
                // Ignore
                break;
            default:
                throw new ReportiumException("Unexpected status " + status);
        }
    }

    /**
     * Creates an instance of a local WebDriver to use during test development phase, and a remote WebDriver instance
     * to use for continuous integration.
     * <p>
     * The decision which type of driver to create is based on an environment variable.
     *
     * @return a new WebDriver instance
     */
    private static WebDriver createDriver() throws MalformedURLException {
        boolean isLocal = Boolean.parseBoolean(System.getProperty(IS_LOCAL_DRIVER, Boolean.FALSE.toString()));
        if (isLocal) {
            return createLocalDriver();
        }
        return createRemoteDriver();
    }

    /**
     * Creates an instance of a local WebDriver to use during test development phase, and a remote WebDriver instance
     * to use for continuous integration.
     * <p>
     * The decision which type of driver to create is based on an environment variable.
     *
     * @return a new WebDriver instance
     */
    private static ReportiumClient createReportiumClient(WebDriver driver) {
        boolean isLocal = Boolean.parseBoolean(System.getProperty(IS_LOCAL_DRIVER, Boolean.FALSE.toString()));
        if (isLocal) {
            return createLocalReportiumClient();
        }
        return createRemoteReportiumClient(driver);
    }

    private static ReportiumClient createRemoteReportiumClient(WebDriver driver) {
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("Sample Reportium project", "1.0"))
                .withContextTags("sanity")
                .withWebDriver(driver)
                .build();
        return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
    }

    private static WebDriver createRemoteDriver() throws MalformedURLException {
        // Read connection details from env variables that are injected during the build process
        // to prevent them from being leaked here

        String seleniumGridUrl = System.getProperty(SELENIUM_GRID_URL_KEY, "https://MYCOMPANY.perfectomobile.com/nexperience/perfectomobile/wd/hub");
        String seleniumGridUsername = System.getProperty(SELENIUM_GRID_USERNAME_KEY, "MYUSER");
        String seleniumGridPassword = System.getProperty(SELENIUM_GRID_PASSWORD_KEY, "MYPASSWORD");

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("user", seleniumGridUsername);
        desiredCapabilities.setCapability("password", seleniumGridPassword);
        desiredCapabilities.setCapability("platformName", "Windows");
        desiredCapabilities.setCapability("platformVersion", "7");
        desiredCapabilities.setCapability("browserName", "Chrome");
        desiredCapabilities.setCapability("browserVersion", "50");
        desiredCapabilities.setCapability("resolution", "1366x768");
        desiredCapabilities.setCapability("location", "US East");
        WebDriver driver = new org.openqa.selenium.remote.RemoteWebDriver(
                new HttpCommandExecutor(new URL(seleniumGridUrl)), desiredCapabilities);

        return driver;
    }

    private static WebDriver createLocalDriver() {
        return new FirefoxDriver();
    }

    private static ReportiumClient createLocalReportiumClient() {
        return new ReportiumClientFactory().createLoggerClient();
    }

    /**
     * Replaces driver.findElement with a WebDriverWait
     *
     * @param by object describing the element to find
     * @return The element identified by the given by
     */
    protected WebElement findElement(By by) {
        long timeOutSeconds = 3;
        long sleepMillis = 500;
        return new WebDriverWait(driver, timeOutSeconds)
                .pollingEvery(sleepMillis, TimeUnit.MILLISECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

}
