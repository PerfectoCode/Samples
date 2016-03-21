package com.perfecto.testing;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WaipaMembershipTestNG {

	private RemoteWebDriver driver;
	public static String USER_NAME = "MY_USER";
	public static String PASSWORD = "MY_PASSWORD";
	public static String PERFECTO_HOST = "MY_HOST.perfectomobile.com";

	@BeforeTest
	public void initDriver() throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities();

        // TODO: Set your cloud host and credentials
		capabilities.setCapability("user", USER_NAME);
		capabilities.setCapability("password", PASSWORD);

		// TODO: Set the Web Machine configuration
		capabilities.setCapability("platformName", "Windows");
		capabilities.setCapability("platformVersion", "7");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("browserVersion", "48");

		// TODO: Name your script
        capabilities.setCapability("scriptName", "WaipaMembershipRegistration");

		// Create Remote WebDriver
        RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub"), capabilities);

		// Define execution timeouts
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

		// Maximize browser window
		driver.manage().window().maximize();
	}

	@Test
	public void testWaipa() throws InterruptedException {
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
	}

	public void scrollToWebElement(RemoteWebDriver driver, WebElement element) {
		// Scroll to web element and click it
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void scrollToBottomOfPage(RemoteWebDriver driver) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("scrollBy(0,10000)");
	}

	@AfterTest
	public void quitDriver() throws Exception {
		// Close Driver
		driver.close();
		System.out.println("Driver Closed");

		// Quit Driver
		driver.quit();
		System.out.println("Driver Quit");
	}

}
