//package com.perfectomobile.pm.testng.selenium;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.perfectomobile.selenium.util.EclipseConnector;

import org.openqa.selenium.support.ui.Select;

import io.appium.java_client.*;
import io.appium.java_client.android.*;
import io.appium.java_client.ios.*;

public class Register2Airbnb {

    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println("Run started");

        String browserName = "mobileOS";
        
        //Opening desktop driver
        DesiredCapabilities desktopCapabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
        String HOST = "yourCloud.perfectomobile.com";
        String USER = "yourUser";
        String PSWD = "yourPassword";
        desktopCapabilities.setCapability("user", USER);
        desktopCapabilities.setCapability("password", PSWD);

        desktopCapabilities.setCapability("platformName", "Windows");
        desktopCapabilities.setCapability("platformVersion", "8.1");
        desktopCapabilities.setCapability("browserName", "Chrome");
        desktopCapabilities.setCapability("browserVersion", "50");
        desktopCapabilities.setCapability("resolution", "1440x900");
        desktopCapabilities.setCapability("location", "US East");

        desktopCapabilities.setCapability("scriptName", "Register2Airbnb");

        // Call this method if you want the script to share the devices with the Perfecto Lab plugin.
        //setExecutionIdCapability(desktopCapabilities, HOST);

        RemoteWebDriver desktopDriver = new RemoteWebDriver(new URL("https://" + HOST + "/nexperience/perfectomobile/wd/hub"), desktopCapabilities);
        desktopDriver.context("WEBVIEW");

        desktopDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        desktopDriver.manage().window().maximize();
        desktopDriver.get("https://www.airbnb.com/");

        //Opening mobile driver
        DesiredCapabilities mobileCapabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
        mobileCapabilities.setCapability("user", USER);
        mobileCapabilities.setCapability("password", PSWD);

        // This is a specific device containing a SIM card
        mobileCapabilities.setCapability("deviceName", "device id os mobile with SIM");
        String mobileNumberNoCountry = "device phone number - no country prefix";

        // Call this method if you want the script to share the devices with the Perfecto Lab plugin.
        //setExecutionIdCapability(mobileCapabilities, HOST);

        //use SMS app package name for android devices
        mobileCapabilities.setCapability("bundleId", "com.apple.MobileSMS");
        //use AndroidDriver for android devices
        IOSDriver mobileDriver = new IOSDriver(new URL("https://" + HOST + "/nexperience/perfectomobile/wd/hub"), mobileCapabilities);
		switchToContext(mobileDriver, "NATIVE_APP");
		mobileDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        
        objectsXpaths objXPath = new objectsXpaths();
        
        try {
        	desktopDriver.findElement(objectsXpaths.loginLink).click();
    		WebDriverWait wait = new WebDriverWait(desktopDriver, 15);
        	try {
        		wait.until(ExpectedConditions.visibilityOf(desktopDriver.findElement(objectsXpaths.loginWin)));
    		} catch (TimeoutException t) {
    			System.out.println("Could not find login popup window");
    		}
        	desktopDriver.findElement(objectsXpaths.emailId).sendKeys("yourAirbnbUser");
        	desktopDriver.findElement(objectsXpaths.pswdId).sendKeys("yourAirbnbPassword");
        	desktopDriver.findElement(objectsXpaths.loginBtnId).click();
        	
        	desktopDriver.findElement(objectsXpaths.userIcon).click();
        	desktopDriver.findElement(objectsXpaths.editProfile).click();
        	try {
    			wait.until(ExpectedConditions.visibilityOf(desktopDriver.findElement(objectsXpaths.addCellNo)));
    		} catch (TimeoutException t) {
    			System.out.println("Could not find window");
    		}
        	desktopDriver.findElement(objectsXpaths.addCellNo).click();
        	new Select(desktopDriver.findElement(objectsXpaths.CellCountryId)).selectByVisibleText("United States");
        	desktopDriver.findElement(objectsXpaths.cellNoId).sendKeys(mobileNumberNoCountry);
        	desktopDriver.findElement(objectsXpaths.verifyViaSMSBtn).click();
        	TimeUnit.SECONDS.sleep(5); //give 5 seconds for SMS to arrive at mobile
        	
        	//Now an SMS arrives at the phone to be used for confirmation completion
        	String sentCode = getCodeFromSMS(mobileDriver);

        	//back to desktop Web app
        	desktopDriver.findElement(objectsXpaths.codeViaSMS).sendKeys(sentCode);
        	desktopDriver.findElement(objectsXpaths.verifyBtn).click();
        	desktopDriver.findElement(objectsXpaths.saveBtn).click(); //Save profile

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
    	
                // Retrieve the URL of the Wind Tunnel Report, can be saved to your execution summary and used to download the report at a later point
                //String reportURL = (String)(mobileDriver.getCapabilities().getCapability(WindTunnelUtils.WIND_TUNNEL_REPORT_URL_CAPABILITY));

                mobileDriver.close();
                desktopDriver.close();
                // In case you want to download the report or the report attachments, do it here.
                // PerfectoLabUtils.downloadReport(desktopDriver, "pdf", "C:/test/report");
                // PerfectoLabUtils.downloadAttachment(driver, "video", "C:/test/report/video", "flv");
                // PerfectoLabUtils.downloadAttachment(driver, "image", "C:/test/report/images", "jpg");

            } catch (Exception e) {
                e.printStackTrace();
            }

            mobileDriver.quit();
            desktopDriver.quit();
        }

        System.out.println("Run ended");
    }
    
    private static String isolateSentCode(String smsText)
	{
		String sentCode = "";
		int colonIndex = smsText.indexOf(':');
		colonIndex = smsText.indexOf(':', colonIndex + 1);
		sentCode = smsText.substring(colonIndex + 2,colonIndex + 6);
		return sentCode;
	}
    
    private static String getCodeFromSMS (IOSDriver mobileDriver)
    {
	    //code for if not general message page
		if (mobileDriver.findElement(objectsXpaths.msgMainTitle) == null)
			mobileDriver.findElement(objectsXpaths.msgBackButton).click();
		String smsText = mobileDriver.findElement(objectsXpaths.lastAirbnbMsg).getAttribute("label");
		String sentCode = isolateSentCode(smsText);
		System.out.println("yaron bad person" + sentCode);
		return (sentCode);
    }

    private static void switchToContext(RemoteWebDriver driver, String context) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", context);
        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
    }

    /*private static String getCurrentContextHandle(RemoteWebDriver driver) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        String context =  (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
        return context;
    }*/

    /*private static List<String> getContextHandles(RemoteWebDriver driver) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        List<String> contexts =  (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
        return contexts;
    }*/

    /*private static void setExecutionIdCapability(DesiredCapabilities capabilities, String host) throws IOException {
        EclipseConnector connector = new EclipseConnector();
        String eclipseHost = connector.getHost();
        if ((eclipseHost == null) || (eclipseHost.equalsIgnoreCase(host))) {
            String executionId = connector.getExecutionId();
            capabilities.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);
        }
    }*/
}
