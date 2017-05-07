package com.perfectomobile.appTest;

import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Community sample test including Perfecto Reporting
 */
public class App {
    public static void main(String[] args) throws IOException {

        System.out.println("Run started");

        //TODO: Update credentials Lab & Community app
        String labUser = "MyLabUser";
        String labPassword = "MyLabPassword";
        String communityUser = "MyCommunityUser";
        String communityPassword = "MyCommunityPassword";

        String browserName = "mobileOS";
        DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
        //TODO: change your lab credentials
        //String host = "reporting-test.perfectomobile.com";
        String host = "MyLab.perfectomobile.com";
        capabilities.setCapability("user", labUser);
        capabilities.setCapability("password", labPassword);

        //TODO: Change your device ID
        capabilities.setCapability("deviceName", "MyDeviceID");


        // Use the automationName capability to define the required framework - Appium (this is the default) or PerfectoMobile.
        capabilities.setCapability("automationName", "Appium");

        // Call this method if you want the script to share the devices with the Perfecto Lab plugin.
        PerfectoLabUtils.setExecutionIdCapability(capabilities, host);

        // Script name
        capabilities.setCapability("scriptName", "Perfecto Community");

        // Install Perfecto app
        capabilities.setCapability("app", "PUBLIC:Android/android.perfecto.apk");

        // Open Perfecto app
        capabilities.setCapability("appPackage", "com.bloomfire.android.perfecto");

        AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        // IOSDriver driver = new IOSDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        // Reporting client
        // with generic tag "Android Native App Tests" (for example: user name, team name)
        PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("Sample Reportium project", "1.0"))
                .withContextTags("AndroidNativeAppTests")
                .withWebDriver(driver)
                .build();
        ReportiumClient reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);

        try {

            //START TEST
            //with test "PerfectoCommunityAppLogIn" name and tag "ValidateLogIn"
            reportiumClient.testStart("PerfectoCommunityAppLogIn", new TestContext("ValidateLogIn"));

            //step1: Validate login page
            reportiumClient.stepStart("step1: Validate login page");
            WebDriverWait wait = new WebDriverWait(driver, 15);  // timeout of 15 seconds
            try {
                wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@resource-id='com.bloomfire.android.perfecto:id/sso']")));
            } catch (TimeoutException t) {
                System.out.println("Did not find the Label within explicit wait time");
            }

            // Step2: Login to app
            reportiumClient.stepStart("step2: Login to app");

            // Enter community username
            AndroidElement email = (AndroidElement) driver.findElementByXPath("//*[@resource-id='com.bloomfire.android.perfecto:id/email_address']");
            email.sendKeys(communityUser);
            // Enter community password
            AndroidElement password = (AndroidElement) driver.findElementByXPath("//*[@resource-id='com.bloomfire.android.perfecto:id/password']");

            password.sendKeys(communityPassword);
            // Click Done
            AndroidElement Done = (AndroidElement) driver.findElementByName("Done");
            Done.click();

            // Validate successful login and add assertion to the execution report
            try {
                wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@resource-id='android:id/action_bar_title']")));
                reportiumClient.reportiumAssert("successful login", true);
            } catch (TimeoutException t) {
                System.out.println("Did not find the home screen title");
                reportiumClient.reportiumAssert("unsuccessful login - timeout", false);
            }

            // Step3: Open profile and validate name
            reportiumClient.stepStart("step3: Open profile and validate name");

            // Click menu icon
            driver.findElementByXPath("//*[@resource-id='android:id/action_bar_title']").click();
            // CLick on profile
            driver.findElementByXPath("//*[@text='Profile']").click();

            // Validate "Profile" text in the title bar
            String titleBarText = driver.findElementByXPath("//*[@resource-id='android:id/action_bar_title']").getText();
            Assert.assertEquals("Profile", titleBarText);

            // Step4: Logout and Uninstall app
            reportiumClient.stepStart("step4: Logout and Uninstall app");

            // Click More options icon
            driver.findElementByXPath("//*[@content-desc='More options']").click();
            // Click Settings
            driver.findElementByXPath("//*[@resource-id='android:id/title']").click();
            // click logout
            driver.findElementByXPath("//*[@resource-id='com.bloomfire.android.perfecto:id/item_logout']").click();

            // Uninstall the Perfecto app
            driver.removeApp("com.bloomfire.android.perfecto");

            //STOP TEST
            reportiumClient.testStop(TestResultFactory.createSuccess());

        } catch (Exception | AssertionError e) {
            e.printStackTrace();
            reportiumClient.testStop(TestResultFactory.createFailure("Test stop failure.", e));
        } finally {
            try {
                // Retrieve the URL of the Single Test Report, can be saved to your execution summary and used to download the report at a later point
                //String reportURL = (String)(driver.getCapabilities().getCapability(WindTunnelUtils.SINGLE_TEST_REPORT_URL_CAPABILITY));

                //Open default browser to Report Library
                String reportURL = reportiumClient.getReportUrl();
                System.out.println("Report URL - " + reportURL);
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(new URI(reportURL));
                }

                driver.close();

                // In case you want to download the report or the report attachments, do it here.
                PerfectoLabUtils.downloadReport(driver, "pdf", "C:\\test\\report");
                // PerfectoLabUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
                // PerfectoLabUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

            } catch (Exception e) {
                e.printStackTrace();
            }

            driver.quit();
        }

        System.out.println("Run ended");

    }
}
