package com.perfecto.reporting.sample;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.OperatingSystem;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.junit.AssumptionViolatedException;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Base test for JUnit tests.
 * <p>
 * This class handles the connection to Perfecto reporting and seamlessly reports test start and end to reduce boilerplate code
 * in the actual test cases.
 */
public class AbstractPerfectoSeleniumTestJunit {

    protected static WebDriver driver;
    protected static ReportiumClient reportiumClient;

    private static final String SELENIUM_GRID_URL_KEY = "selenium-grid-url";
    private static final String SELENIUM_GRID_USERNAME_KEY = "selenium-grid-username";
    private static final String SELENIUM_GRID_PASSWORD_KEY = "selenium-grid-password";
    private static final String IS_LOCAL_DRIVER = "is-local-driver";

    protected static final OperatingSystem operatingSystemName = OperatingSystem.WINDOWS;
    protected static final String operatingSystemVersion = "7";
    protected static final String browserVersion = "49";
    protected static final String displayResolution = "1366x768";
    protected static final String location = "US East";

    @ClassRule
    public static ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            driver = createDriver();
            reportiumClient = createReportiumClient(driver);
        }

        @Override
        protected void after() {
            if (driver != null) {
                //  Get link to report
                driver.quit();
            }
        }
    };

    @Rule
    public TestRule watchman = new TestWatcher() {

        @Override
        protected void succeeded(Description description) {
            reportiumClient.testStop(TestResultFactory.createSuccess());
        }

        @Override
        protected void failed(Throwable e, Description description) {
            reportiumClient.testStop(TestResultFactory.createFailure("An error occurred", e));
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            // Do nothing until skipped tests are modeled in Reportium
        }

        @Override
        protected void starting(Description description) {
            try {
                String testName = description.getTestClass().getSimpleName() + "." + description.getMethodName();
                // Get JUnit group name
                Category categoryAnnotation = description.getTestClass().getAnnotation(Category.class);
                TestContext testContext;

                if (categoryAnnotation != null) {
                    // Add category value as a tag
                    Class<?>[] categoryClasses = categoryAnnotation.value();
                    String[] tags = new String[categoryClasses.length];
                    for (int i = 0; i < categoryClasses.length; i++) {
                        tags[i] = categoryClasses[i].getSimpleName();
                    }
                    testContext = new TestContext(tags);
                } else {
                    testContext = new TestContext();
                }
                reportiumClient.testStart(testName, testContext);
            } catch (RuntimeException e) {
                System.err.println("Failed to submit test start to reportium: " + e.getMessage());
                throw e;
            }
        }
    };

    /**
     * Creates an instance of a local WebDriver to use during test development phase, and a remote WebDriver instance
     * to use for continuous integration.
     * <p>
     * The decision which type of driver to create is based on an environment variable.
     *
     * @return a new WebDriver instance
     */
    private static WebDriver createDriver() throws MalformedURLException {
        if (isLocal()) {
            return createLocalDriver();
        }
        return createRemoteDriver();
    }

    private static ReportiumClient createReportiumClient(WebDriver driver) {
        if (isLocal()) {
            return createLocalReportiumClient();
        }
        return createRemoteReportiumClient(driver);
    }

    /**
     * Returns true if the run is using the resources of this PC (local webdriver, local Reportium client)
     *
     * @return
     */
    private static boolean isLocal() {
        return Boolean.parseBoolean(System.getProperty(IS_LOCAL_DRIVER, Boolean.TRUE.toString()));
    }

    /**
     * Creates a {@link org.openqa.selenium.remote.RemoteWebDriver} instance for executing the Selenium script
     *
     * @return Webdriver to use for this test
     * @throws MalformedURLException
     */
    private static WebDriver createRemoteDriver() throws MalformedURLException {
        // Read connection details from env variables that are injected during the build process
        // to prevent them from being leaked here
        String seleniumGridUrl = SystemPropertyGetter.getProperty(SELENIUM_GRID_URL_KEY);
        String seleniumGridUsername = SystemPropertyGetter.getProperty(SELENIUM_GRID_USERNAME_KEY);
        String seleniumGridPassword = SystemPropertyGetter.getProperty(SELENIUM_GRID_PASSWORD_KEY);

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("user", seleniumGridUsername);
        desiredCapabilities.setCapability("password", seleniumGridPassword);
        desiredCapabilities.setCapability("platformName", operatingSystemName);
        desiredCapabilities.setCapability("platformVersion", operatingSystemVersion);
        desiredCapabilities.setCapability("browserName", "Chrome");
        desiredCapabilities.setCapability("browserVersion", browserVersion);
        desiredCapabilities.setCapability("resolution", displayResolution);
        desiredCapabilities.setCapability("location", location);

        WebDriver driver = new org.openqa.selenium.remote.RemoteWebDriver(
                new HttpCommandExecutor(new URL(seleniumGridUrl)), desiredCapabilities);

        return driver;
    }

    /**
     * Creates a {@link ReportiumClient} that
     *
     * @param driver
     * @return
     */
    private static ReportiumClient createRemoteReportiumClient(WebDriver driver) {
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("Sample Reportium project", "1.0"))
                .withWebDriver(driver)
                .build();
        return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
    }

    /**
     * Creates a local {@link FirefoxDriver} instance
     *
     * @return a local {@link FirefoxDriver} instance
     */
    private static WebDriver createLocalDriver() {
        return new FirefoxDriver();
    }

    /**
     * @return a {@link ReportiumClient} implementation that uses a {@link java.util.logging.Logger} instance
     */
    private static ReportiumClient createLocalReportiumClient() {
        return new ReportiumClientFactory().createLoggerClient();
    }
}
