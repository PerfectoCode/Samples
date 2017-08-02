
/**
 * @author Lee Shoham
 * @date Jul 26, 2016
 */

import java.io.BufferedReader; 
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import au.com.bytecode.opencsv.CSVReader;
import jxl.Sheet;

public class DynamicXpathAndValues {
	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println("Run started");
		
		String browserName = "mobileOS";
		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		String host = "cloudName.perfectomobile.com";
		capabilities.setCapability("user", "user@perfectomobile.com");
		capabilities.setCapability("password", "password");

		// TODO: Change your device ID
		capabilities.setCapability("deviceName", "device#");

		// Use the automationName capability to define the required framework -
		// Appium (this is the default) or PerfectoMobile.
		capabilities.setCapability("automationName", "PerfectoMobile");

		// Call this method if you want the script to share the devices with the
		// Perfecto Lab plugin.
		PerfectoLabUtils.setExecutionIdCapability(capabilities, host);

		RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"),
				capabilities);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);		
		
		try {
			// Navigating to website
			driver.get("https://www.national-lottery.co.uk/games/lotto");

			// Getting location of wanted element to scroll to and scrolling
			Point elem = driver.findElement(By.linkText("Add lines")).getLocation();
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + (elem.getY() - 300) + ");");

			// Inserting values using dynamic xpath and external CSV file for
			// values
			 CSVReader reader = new CSVReader(new
			 FileReader("codebeautify.csv"));
			 String[] nextLine;

			// moving 1 line down since the first line is headers
			 nextLine = reader.readNext();

			// iterating over the table lines and building the dynamic xpath
			// using column# and row# (line[0] and line[1]) and extracting value
			// (line[2])
			 while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line //
			 String xpath = "(//input[@id='lotto_playslip_line_" + nextLine[1]
			 + "_pool_0_col_" + nextLine[0] + "'])[1]";
			 driver.findElement(By.xpath(xpath)).sendKeys(nextLine[2]);
			 }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
				driver.close();
				// Download a pdf version of the execution report
				PerfectoLabUtils.downloadReport(driver, "pdf", "C:\\Users\\user\\Desktop\\report");

			} catch (Exception e) {
				e.printStackTrace();
			}

			driver.quit();
		}

		System.out.println("Run ended");
	}
}
