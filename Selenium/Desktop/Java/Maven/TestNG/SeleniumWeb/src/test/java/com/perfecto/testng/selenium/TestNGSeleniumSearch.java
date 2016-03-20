package com.perfecto.testng.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.perfecto.testng.utils.GenericUtils;
import com.perfecto.testng.utils.PerfectoLabUtils;

public class TestNGSeleniumSearch {
	private RemoteWebDriver driver;

	@BeforeMethod
	public void initDriver() throws Exception {
		
		// Cloud host and credentials
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("user", GenericUtils.USER);
		capabilities.setCapability("password", GenericUtils.PASSWORD);
		
		// Web Machine configuration
		capabilities.setCapability("platformName", "Windows");
		capabilities.setCapability("platformVersion", "7");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("version", "47");
		capabilities.setCapability("resolution", "1366x768");
		capabilities.setCapability("location", "US East");	
		
		// Name of script
		capabilities.setCapability("scriptName", "GoogleSearchSanity");

		System.out.println("Creating driver, START:, " + GenericUtils.getCurrentTime());
		driver = new RemoteWebDriver(new URL(GenericUtils.HOST), capabilities);
		System.out.println("Creating driver, DONE:, " + GenericUtils.getCurrentTime());		
	}

	@Test
	public void searchTestNGInGoogle() throws MalformedURLException {		
		// Maximize browser window
		driver.manage().window().maximize();
		System.out.println("Browser Window was maximized");
		
		// Search Google
		final String searchKey = "Perfecto Mobile";
		System.out.println("Search " + searchKey + " in google");
		driver.navigate().to("http://www.google.com");
		WebElement element = driver.findElement(By.name("q"));
		System.out.println("Enter " + searchKey);
		element.sendKeys(searchKey);
		System.out.println("submit");
		element.submit();
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase()
						.startsWith(searchKey.toLowerCase());
			}
		});
		System.out.println("Got " + searchKey + " results");
	}

	@AfterMethod
	public void quitDriver() throws Exception {
		driver.close();
		System.out.println("Driver Closed");
		
		Map<String, Object> params = new HashMap<String, Object>();
        	driver.executeScript("mobile:execution:close", params);
        
        	PerfectoLabUtils.downloadReport(driver, "html", GenericUtils.REPORT_PATH + "Report");
        
		driver.quit();
		System.out.println("Driver Quit");
	}
}
