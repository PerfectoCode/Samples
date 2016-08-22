package com.perfectomobile.appTest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//import junit.framework.Assert;

import org.openqa.selenium.Platform;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait; 
import org.testng.Assert;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        
        System.out.println("Run started");

        String browserName = "mobileOS";
        DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
        //TODO: change your lab credentials
        String host = "myLab.perfectomobile.com";
        capabilities.setCapability("user", "myUser");
        capabilities.setCapability("password", "myPassword");

        //TODO: Change your device ID
        capabilities.setCapability("deviceName", "LGH820E79307F1");

        // Use the automationName capability to define the required framework - Appium (this is the default) or PerfectoMobile.
        capabilities.setCapability("automationName", "Appium");

        // Call this method if you want the script to share the devices with the Perfecto Lab plugin.
        PerfectoLabUtils.setExecutionIdCapability(capabilities, host);
     
        // Script name
        capabilities.setCapability("scriptName", "PerfectoCommunity");
        
        // Install Perfecto app
        capabilities.setCapability("app", "PUBLIC:Android/android.perfecto.apk");
        
        // Open Perfecto app
        capabilities.setCapability("appPackage", "com.bloomfire.android.perfecto");

        AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        // IOSDriver driver = new IOSDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        try {
        	
        	//step1: Validate login page
        	WebDriverWait wait = new WebDriverWait(driver, 15);  // timeout of 15 seconds
        	 try {
        	      wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@resource-id='com.bloomfire.android.perfecto:id/sso']")));
        	 } catch (TimeoutException t) {
        	       System.out.println("Did not find the Label within explicit wait time");
        	 }
            
            // Step2: Login to app
        	 
            //TODO: Change your Perfecto community credentials
        	// Enter username
        	AndroidElement email = (AndroidElement) driver.findElementByXPath("//*[@resource-id='com.bloomfire.android.perfecto:id/email_address']");
     		email.sendKeys("myCommunityUser");
     		// Enter password
     		AndroidElement password = (AndroidElement) driver.findElementByXPath("//*[@resource-id='com.bloomfire.android.perfecto:id/password']");
     		password.sendKeys("myCommunityPassword");            
            // Click Done
     		AndroidElement Done = (AndroidElement) driver.findElementByName("Done");
    		Done.click();
            
            // Validate successful login
     		try {
				wait.until(ExpectedConditions.visibilityOf(driver.findElementByXPath("//*[@resource-id='android:id/action_bar_title']")));
			} catch (TimeoutException t) {
				System.out.println("Did not find the home screen title");
			}
            
            // Step3: Open profile and validate name
            
            // Click menu icon
            driver.findElementByXPath("//*[@resource-id='android:id/action_bar_title']").click();           
            // CLick on profile
            driver.findElementByXPath("//*[@text='Profile']").click();           
            
            // Validate "Profile" text in the title bar
            String titleBarText = driver.findElementByXPath("//*[@resource-id='android:id/action_bar_title']").getText();
            Assert.assertEquals("Profile", titleBarText);
                        
            // Step4: Logout and Uninstall app
            
            // Click More options icon
            driver.findElementByXPath("//*[@content-desc='More options']").click();
            // Click Settings
            driver.findElementByXPath("//*[@resource-id='android:id/title']").click();
            // click logout
            driver.findElementByXPath("//*[@resource-id='com.bloomfire.android.perfecto:id/item_logout']").click();
            
            // Uninstall the Perfecto app
            driver.removeApp("com.bloomfire.android.perfecto");            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Retrieve the URL of the Single Test Report, can be saved to your execution summary and used to download the report at a later point
                //String reportURL = (String)(driver.getCapabilities().getCapability(WindTunnelUtils.SINGLE_TEST_REPORT_URL_CAPABILITY));

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
