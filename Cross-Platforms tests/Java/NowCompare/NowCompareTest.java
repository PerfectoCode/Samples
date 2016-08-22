import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.perfectomobile.httpclient.utils.FileUtils;

public class NowCompareTest {

    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println("Run started");

        // TODO: Set your cloud host and credentials
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String host = "yourLab.perfectomobile.com";
        capabilities.setCapability("user", "yourUser");
        capabilities.setCapability("password", "yourPassword");

    	String TARGET_EXECUTION = "Desktop"; // If "Desktop" create Web Machine, else run on Mobile browser
    	//String TARGET_EXECUTION = "Mobile"; // If "Desktop" create Web Machine, else run on Mobile browser
    	
    	if (TARGET_EXECUTION == "Desktop") {
    		// Define Web Machine Under Test
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "40");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
    	} else {
            capabilities.setCapability("deviceName", "deviceName");
    	}
	
    	// TODO: Name your script
        capabilities.setCapability("scriptName", "NowCompareTest");

        RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        // Maximize for desktop browsers
		if (TARGET_EXECUTION == "Desktop")
			driver.manage().window().maximize();

        try {
            // TODO: write your code here
    		System.out.println("Open NowCompare Insurance web site");
    		driver.get("https://www.nowcompare.com/");
    		WebDriverWait wait = new WebDriverWait(driver, 15);
    		WebElement element = null;

    		//take screenshot
    		File source;
    		String screenshotFile;
    		
    		if (TARGET_EXECUTION == "Desktop") {
        		//In desktop select Trade directly from top menu - just verify visibility and...
        		try {
        			wait.until(ExpectedConditions.visibilityOf(driver.findElement
        				(By.xpath("//*[ancestor::*[@id='dq-middle'] and @title='Health-Insurance']"))));
        		} catch (TimeoutException t) {
        			System.out.println("Could not find element");
        		}
        		//take screenshot
        		source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        		screenshotFile = "C:/Temp/" + source.getName() + "-NowComp-" + TARGET_EXECUTION;
        		FileUtils.copyFile(source, new File(screenshotFile));
    		}  else {
        		//Verify visibility of hamburger menu, and take screenshot     
        		try {
        			wait.until(ExpectedConditions.visibilityOf(driver.findElement
        				(By.xpath("//*[ancestor::*[@id='dq-middle'] and @class='navbar-toggle']"))));
        		} catch (TimeoutException t) {
        			System.out.println("Could not find element");
        		}

        		//then open hamburger menu and take screenshot of menu...
        		element = driver.findElement(By.xpath("//*[ancestor::*[@id='dq-middle'] and @class='navbar-toggle']"));
        		element.click();
        		
           		try {
           			wait.until(ExpectedConditions.visibilityOf(driver.findElement
           				(By.xpath("//*[ancestor::*[@id='dq-middle'] and @title='Health-Insurance']"))));
           		} catch (TimeoutException t) {
           			System.out.println("Could not find element");
           		}
        		source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        		screenshotFile = "C:/Temp/" + source.getName() + "-NowComp-hamburger-" + TARGET_EXECUTION;
        		FileUtils.copyFile(source, new File(screenshotFile));
        		System.out.println("Opened hamburger menu");        		
        	}


       		element = driver.findElement(By.xpath("//*[ancestor::*[@id='dq-middle'] and @title='Health-Insurance']"));
       		element.click();
       		System.out.println("Clicked +Health");
        		
       		//Verify visibility of Health insurance page and take screenshot
       		try {
       			wait.until(ExpectedConditions.visibilityOf(driver.findElement
       				(By.xpath("//*[ancestor::*[@id='dq-middle'] and text()='Compare Expat Health Insurance']"))));
       		} catch (TimeoutException t) {
       			System.out.println("Could not find element");
       		}

       		source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
       		screenshotFile = "C:/Temp/" + source.getName() + "-NowComp-Health-" + TARGET_EXECUTION;
       		FileUtils.copyFile(source, new File(screenshotFile));
        	
       		//Fill in the quote request form
       		element = driver.findElement(By.xpath("//button[ancestor::*[@id='preqoute_form'] and parent::*[@class='icon-number']]"));
       		element.click();
       		element = driver.findElement(By.xpath("//*[preceding-sibling::*[@id='ui-multiselect-fPerson-option-6'] and text()='6']"));
       		element.click();
       		element = driver.findElement(By.xpath("//button[ancestor::*[@id='preqoute_form'] and parent::*[@class='icon-location']]"));
       		element.click();
       		element = driver.findElement(By.xpath("//*[preceding-sibling::*[@id='ui-multiselect-fLocation-option-241'] and text()='United Kingdom']"));
       		element.click();
       		element = driver.findElement(By.xpath("//button[ancestor::*[@id='preqoute_form'] and parent::*[@class='icon-requirements']]"));
       		element.click();
       		element = driver.findElement(By.xpath("//*[@id='ui-multiselect-cover-list-option-2' and following-sibling::*[text()='Dental Cover']]"));
       		if ((! element.isSelected()) && element.isEnabled())
       			element.click();
       		element = driver.findElement(By.xpath("//*[@id='ui-multiselect-cover-list-option-3' and following-sibling::*[text()='Maternity Cover']]"));
       		if ((! element.isSelected()) && element.isEnabled())
       			element.click();
       		element = driver.findElement(By.xpath("//*[parent::*[@id='preqoute_form'] and text()='GET QUOTES']"));
       		element.click();
       		
       		//Verify visibility of insured people page and take screenshot
       		try {
       			wait.until(ExpectedConditions.visibilityOf(driver.findElement
       				(By.xpath("//*[ancestor::*[@id='dq-page-holder'] and text()='Your Details']"))));
       		} catch (TimeoutException t) {
       			System.out.println("Could not find element");
       		}

       		source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
       		screenshotFile = "C:/Temp/" + source.getName() + "-NowComp-details-" + TARGET_EXECUTION;
       		FileUtils.copyFile(source, new File(screenshotFile));
          } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                driver.close();

                // In case you want to down the report or the report attachments, do it here.
                 Map<String, Object> params = new HashMap<>(); 
                 driver.executeScript("mobile:execution:close", params);
                 PerfectoLabUtils.downloadReport(driver, "pdf", "C:\\test\\NowCompare-report");
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