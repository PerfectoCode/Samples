import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.*;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.Job;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;

/**
 * This template is for users that use DigitalZoom Reporting (ReportiumClient).
 * For any other use cases please see the basic template at https://github.com/PerfectoCode/Templates.
 * For more programming samples and updated templates refer to the Perfecto Documentation at: http://developers.perfectomobile.com/
 */
public class RemoteWebDriverTest {



    public static void main(String[] args) throws IOException {
        System.out.println("Run started");

        String browserName = "";
        DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);

        String host = "CLOUDURL.perfectomobile.com";
        capabilities.setCapability("securityToken", "TOKEN-VALUE");

        //Desired capabiltiites for example:
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "70");
        capabilities.setCapability("location", "US East");
        capabilities.setCapability("resolution", "1024x768");

        //setting ChromeDriver logging to PEFORMANCE as per http://chromedriver.chromium.org/logging/performance-log
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);


        // Use the automationName capability to define the required framework - Appium (this is the default) or PerfectoMobile.
        // capabilities.setCapability("automationName", "PerfectoMobile");

        // Call this method if you want the script to share the devices with the Perfecto Lab plugin.
        PerfectoLabUtils.setExecutionIdCapability(capabilities, host);

        // Name your script
        // capabilities.setCapability("scriptName", "RemoteWebDriverTest");

        RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        // Reporting client. For more details, see http://developers.perfectomobile.com/display/PD/Reporting
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("My Project", "1.0"))
                .withJob(new Job("My Job", 45))
                .withContextTags("tag1")
                .withWebDriver(driver)
                .build();
        ReportiumClient reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);

        try {
            reportiumClient.testStart("ChromePerformanceLogSeleniumTest", new TestContext("tag2", "tag3"));



            System.out.println(driver.manage().logs().getAvailableLogTypes());
            driver.get("https://google.com/");
            for (LogEntry logEntry : driver.manage().logs().get(LogType.PERFORMANCE).getAll())
            {
                System.out.println(logEntry);
            }


            reportiumClient.testStop(TestResultFactory.createSuccess());
        } catch (Exception e) {
            reportiumClient.testStop(TestResultFactory.createFailure(e.getMessage(), e));
            e.printStackTrace();
        } finally {
            try {
                driver.quit();



                // Retrieve the URL to the DigitalZoom Report (= Reportium Application) for an aggregated view over the execution
                String reportURL = reportiumClient.getReportUrl();

                // Retrieve the URL to the Execution Summary PDF Report
                String reportPdfUrl = (String)(driver.getCapabilities().getCapability("reportPdfUrl"));
                // For detailed documentation on how to export the Execution Summary PDF Report, the Single Test report and other attachments such as
                // video, images, device logs, vitals and network files - see http://developers.perfectomobile.com/display/PD/Exporting+the+Reports

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Run ended");
    }



    private static void switchToContext(RemoteWebDriver driver, String context) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", context);
        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
    }

    private static String getCurrentContextHandle(RemoteWebDriver driver) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        String context =  (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
        return context;
    }

    private static List<String> getContextHandles(RemoteWebDriver driver) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        List<String> contexts =  (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
        return contexts;
    }
}
