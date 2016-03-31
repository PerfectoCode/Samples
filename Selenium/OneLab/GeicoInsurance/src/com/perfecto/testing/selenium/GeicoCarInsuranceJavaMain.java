package com.perfecto.testing.selenium;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.Select;

public class GeicoCarInsuranceJavaMain {

	private static RemoteWebDriver driver;
	public static String USER_NAME = "MY_USER";
	public static String PASSWORD = "MY_PASSWORD";
	public static String PERFECTO_HOST = "MY_HOST.perfectomobile.com";
	
	private static String TARGET_EXECUTION = "Desktop"; // If "Desktop" create Web Machine, else run on Mobile browser

	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println("Run started");

		// Create Remote WebDriver based on target execution platform
		if (TARGET_EXECUTION == "Desktop") {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// Cloud User/Pass
			capabilities.setCapability("user", USER_NAME);
			capabilities.setCapability("password", PASSWORD);

			// Target Web Machine configuration
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "45");

			// Additional capabilities
			capabilities.setCapability("resolution", "1440x900");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("takesScreenshot", true);
			
			// Name of script
			capabilities.setCapability("scriptName", "GeicoCarInsuranceDesktopWeb");
			
			// Create Remote WebDriver
			System.out.println("Creating Web Machine per specified capabilities");
			driver = new RemoteWebDriver(new URL("https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub"), capabilities);
		} else {

			// Define target mobile device
			String browserName = "mobileOS";
			DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
			capabilities.setCapability("user", USER_NAME);
			capabilities.setCapability("password", PASSWORD);
			capabilities.setCapability("deviceName", "1234567890"); // Set your target Device ID
			
			// Define device allocation timeout, in minutes
			capabilities.setCapability("openDeviceTimeout", 5);
			
			// Name of script
			capabilities.setCapability("scriptName", "GeicoCarInsuranceMobileWeb");
			
			// Create Remote WebDriver
			System.out.println("Allocating Mobile device per specified capabilities");
			driver = new RemoteWebDriver(new URL("https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub"), capabilities);
		}
		
		// Define execution timeouts
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		try {			
			// Navigate to geico.com
			driver.get("http://geico.com");

			// Maximize browser window on desktop
			if(TARGET_EXECUTION == "Desktop")
                driver.manage().window().maximize();

			// Enter form data
			Select type = new Select(driver.findElement(By.id("insurancetype")));

			type.selectByVisibleText("Motorcycle");
			driver.findElement(By.id("zip")).sendKeys("01434");
			driver.findElement(By.id("submitButton")).click();

			driver.findElement(By.id("firstName")).sendKeys("MyFirstName");
			driver.findElement(By.id("lastName")).sendKeys("MyFamilyName");
			driver.findElement(By.id("street")).sendKeys("My Address");

			driver.findElement(By.id("date-monthdob")).sendKeys("8");
			driver.findElement(By.id("date-daydob")).sendKeys("3");
			driver.findElement(By.id("date-yeardob")).sendKeys("1981");

			// Set Radio buttons, 1-Yes; 2-No
			driver.findElement(By.xpath("//*[@class='radio'][2]")).click();
			driver.findElement(By.id("btnSubmit")).click();

			driver.findElement(By.id("hasCycle-error")).isDisplayed();

			// select yes
			Select hasCycle = new Select(driver.findElement(By.id("hasCycle")));
			hasCycle.selectByIndex(1);

			// new select added (which current company)
			Select current = new Select(driver.findElement(By.id("currentInsurance")));
			current.selectByVisibleText("Other");
			driver.findElement(By.id("btnSubmit")).click();

			System.out.println("Done geico.com\n");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				driver.close();

				if (TARGET_EXECUTION == "Desktop") {
					Map<String, Object> params = new HashMap<String, Object>();
					driver.executeScript("mobile:execution:close", params);
				}
				// In case you want to download the report or the report attachments, do it here.
				// PerfectoLabUtils.downloadAttachment(driver, "video", "C:/test/report/video", "flv");
				// PerfectoLabUtils.downloadAttachment(driver, "image", "C:/test/report/images", "jpg");

			} catch (Exception e) {
				e.printStackTrace();
			}

			driver.quit();
		}

		System.out.println("Run ended");
	}
}
