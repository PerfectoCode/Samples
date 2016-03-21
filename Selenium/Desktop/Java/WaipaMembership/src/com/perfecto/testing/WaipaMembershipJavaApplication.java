package com.perfecto.testing;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.Select;

public class WaipaMembershipJavaApplication {

	public static String USER_NAME = "MY_USER";
	public static String PASSWORD = "MY_PASSWORD";
	public static String PERFECTO_HOST = "MY_HOST.perfectomobile.com";
	
	public static void main(String[] args) throws MalformedURLException {
		System.out.println("Run started");

		// TODO: Set your cloud host and credentials
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("user", USER_NAME);
		capabilities.setCapability("password", PASSWORD);

		// TODO: Set the Web Machine configuration
		capabilities.setCapability("platformName", "Windows");
		capabilities.setCapability("platformVersion", "7");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("browserVersion", "48");

		// TODO: Name your script
		capabilities.setCapability("scriptName", "WaipaMembershipRegistration");

		RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		try {
			// Navigate to waipa.org
			System.out.println("Open waipa.org");
			driver.get("http://waipa.org/");

			WebElement element = driver.findElement(By.linkText("MEMBERSHIP"));
			element.click();
			System.out.println("Clicked Membership");

			// Click on Register
			element = driver.findElement(By.xpath(".//*[@id='membership']/div[3]/a"));
			element.click();

			// Select "Are You?" from the list
			element = driver.findElement(By.name("AreYou"));
			driver.findElement(By.name("AreYou")).click();
			new Select(element).selectByVisibleText("Company/Firm");
			element = driver.findElement(By.xpath("//option[@value='Company/Firm']"));
			element.click();

			// Set Family Name
			element = driver.findElement(By.name("FamilyName"));
			element.sendKeys("Asaf");

			// Set First Name
			element = driver.findElement(By.xpath("(//input[@name='firstname'])[2]"));
			element.sendKeys("Saar");

			// Set Institution Name
			element = driver.findElement(By.name("Institution"));
			element.sendKeys("Perfecto Mobile");

			// set Nature of Business
			element = driver.findElement(By.xpath("//div[@id='wpcf7-f134-o1']/form/div[4]/div/span/input"));
			element.sendKeys("Seek Perfection");

			// Set Phone
			element = driver.findElement(By.xpath("//div[@id='wpcf7-f134-o1']/form/div[4]/div[2]/span/input"));
			element.sendKeys("+1-555-0000");

			// Set Email
			element = driver.findElement(By.xpath("//div[@id='wpcf7-f134-o1']/form/div[5]/div/span/input"));
			element.sendKeys("contactus@perfectomobile.com");

			// Set address
			element = driver.findElement(By.xpath("//div[@id='wpcf7-f134-o1']/form/div[5]/div[2]/span/input"));
			element.sendKeys("120 Presidential Way, Suite 110");

			// Set Post code
			element = driver.findElement(By.xpath("//div[@id='wpcf7-f134-o1']/form/div[5]/div[3]/span/input"));
			element.sendKeys("01801");

			// Set City
			element = driver.findElement(By.xpath("//div[@id='wpcf7-f134-o1']/form/div[6]/div/span/input"));
			element.sendKeys("Woburn");

			// Set Country
			element = driver.findElement(By.name("country"));
			element.sendKeys("MA");

			// Set Web site
			element = driver.findElement(By.xpath("//div[@id='wpcf7-f134-o1']/form/div[6]/div[3]/span/input"));
			element.sendKeys("http://www.perfectomobile.com/");

			// Click Submit
			element = driver.findElement(By.xpath("//input[@value='Submit']"));
			element.click();

			// Get error message
			Thread.sleep(2000);
			element = driver.findElement(By.xpath("//*[@id='wpcf7-f134-o1']/form/div[10]"));
			System.out.println("Error message shown: " + element.getText());

			System.out.println("Done waipa.org\n");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				driver.close();

	        	Map<String, Object> params = new HashMap<>(); 
                driver.executeScript("mobile:execution:close", params);

                // In case you want to down the report or the report attachments, do it here.
                // PerfectoLabUtils.downloadReport(driver, "pdf", "C:\\test\\report");
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