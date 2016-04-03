package com.perfecto.testing.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Utils {
    public static String REPORT_LIB = "C:/Development/Reports/";
    static String SCREENSHOTS_LIB = "C:/Development/Reports/images/";
    
    public static String USER_NAME = "MY_USER";
    public static String PASSWORD = "MY_PASSWORD";
    public static String PERFECTO_HOST = "MY_HOST.perfectomobile.com";

	public static RemoteWebDriver getRemoteWebDriver(String platformName, String platformVersion, String browserName,
			String browserVersion, String deviceId) throws MalformedURLException {

		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);

		capabilities.setCapability("user", USER_NAME);
		capabilities.setCapability("password", PASSWORD);
		if (!deviceId.isEmpty()) {
			browserName = "mobileOS";
			capabilities.setCapability("deviceName", deviceId);
			System.out.println("Creating Remote WebDriver on: " + deviceId);
		}

		if (!platformName.isEmpty()) {
			capabilities.setCapability("platformName", platformName);
			capabilities.setCapability("platformVersion", platformVersion);
			capabilities.setCapability("browserName", browserName);
			capabilities.setCapability("browserVersion", browserVersion);
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");

			System.out.println("Creating Remote WebDriver on: " + platformName + " " + platformVersion + ", " + browserName + " " + browserVersion);
		}

		// Define test name
		capabilities.setCapability("scriptName", "DigitalWebScenarioParallel");

		RemoteWebDriver webdriver = new RemoteWebDriver(
				new URL("https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub"), capabilities);

		// Define RemoteWebDriver timeouts
		webdriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		webdriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		// Maximize browser window on Desktop
		if (!platformName.isEmpty()) {
			webdriver.manage().window().maximize();
		}

		return webdriver;
	}


}
