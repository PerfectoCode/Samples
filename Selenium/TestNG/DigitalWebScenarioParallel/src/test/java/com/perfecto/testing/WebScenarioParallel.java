package com.perfecto.testing;

import java.io.IOException;
import java.net.MalformedURLException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.perfecto.testing.utils.Utils;

public class WebScenarioParallel {
	RemoteWebDriver driver;

	// Create Remote WebDriver based on testng.xml configuration
	@Parameters({ "platformName", "platformVersion", "browserName", "browserVersion", "screenResolution" })
	@BeforeTest
	public void beforeTest(String platformName, String platformVersion, String browserName, String browserVersion, String screenResolution) throws MalformedURLException {
		driver = Utils.getRemoteWebDriver(platformName, platformVersion, browserName, browserVersion, screenResolution);
	}
	
	// Test Method, navigate to google and perform search
	@Test
	public void searchGoogle() throws MalformedURLException {				

		driver.get("http://www.google.com");

		try {
			final String searchKey = "Perfecto Mobile";
			WebElement element = driver.findElement(By.name("q"));
			element.sendKeys(searchKey);
			element.submit();
			(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
					return d.getTitle().toLowerCase().startsWith(searchKey.toLowerCase());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Done: searchGoogle");
	}

	// Test Method, navigate to Geico and get insurance quote
	@Test
	public void geicoInsurance() throws MalformedURLException {

		driver.get("http://www.geico.com");

		try {
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

			driver.findElement(By.xpath("//*[@class='radio'][2]")).click();
			driver.findElement(By.id("btnSubmit")).click();

			driver.findElement(By.id("hasCycle-error")).isDisplayed();

			Select hasCycle = new Select(driver.findElement(By.id("hasCycle")));
			hasCycle.selectByIndex(1);

			Select current = new Select(driver.findElement(By.id("currentInsurance")));
			current.selectByVisibleText("Other");
			driver.findElement(By.id("btnSubmit")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Done: geicoInsurance");
	}

	@AfterTest
	public void afterTest() throws IOException {
		driver.close();
		driver.quit();	
	}	
	
}
