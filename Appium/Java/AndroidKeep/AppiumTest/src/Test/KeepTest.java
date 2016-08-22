package Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import Pages.*;

public class KeepTest {
	private AndroidDriver<MobileElement> driver;
	private String reportName = "KeepTake3";
	
	@DataProvider(name = "NoteLists")
	public static Object[][] notes() {
		return new Object[][] {
			new Object[] {"Creating Automation Test", "Select app to test",
					"Write automation script", "null"},
			new Object[] {"Expand for Reportium", "Update import files", "Add report steps",
					"REM: Tomorrow"},
			new Object[] {"Updating Community Categories", "Decide on set of categories",
					"Review all posts", "REM: Next Week"}
			};
		}
	
	@Parameters({"OperSys", "device", "pkgName", "cloud", "userN", "userPw"})
	@BeforeTest
	public void preTest(String os, String dev, String pkg, 
			String hostC, String uname, String pw) {
		
        try {
			DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);
			String host = hostC;
			// provide Lab credentials
			capabilities.setCapability("user", uname);
			capabilities.setCapability("password", pw);
			// provide application identifier
			capabilities.setCapability("appPackage", pkg);
			// provide device identification
			capabilities.setCapability("deviceName", dev);
			capabilities.setCapability("platformName", os);
			
			// additional capabilities
			capabilities.setCapability("automationName", "Appium");
			capabilities.setCapability("scriptName", "KeepTests");

			// Call this method if you want the script to share the devices with the Perfecto Lab plugin.
			PerfectoLabUtils.setExecutionIdCapability(capabilities, host);
			driver = new AndroidDriver<MobileElement>(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (Exception e) {
			e.printStackTrace();
			System.out.println("Unable to initialize the Driver instance!");
		}
	}
	
	@AfterTest
	public void finishUp() {
        try {
            // Retrieve the URL of the Single Test Report, can be saved to your execution summary and used to download the report at a later point
            // String reportURL = (String)(driver.getCapabilities().getCapability(WindTunnelUtils.SINGLE_TEST_REPORT_URL_CAPABILITY));
        	driver.closeApp();
            driver.close();

            // In case you want to download the report or the report attachments, do it here.
            PerfectoLabUtils.downloadReport(driver, "pdf", "C:\\test\\report" + reportName);
            // PerfectoLabUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
            // PerfectoLabUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
        System.out.println("Run ended");		
	}
	
	@Test(dataProvider = "NoteLists")
	public void createNotes(String title, String item1, String item2, String rem) {
		StickyBoard first = new StickyBoard(driver);
		NoteEditor edit = first.createNewList();
		edit.addTitle(title);
		edit.enterItem(item1);
		edit.addItem(item2);
		if (rem.startsWith("REM:")) {
			// set a reminder based on continuation of string - either "Today", "Tomorrow", or "Next Week"
			String whn = rem.substring(5);
			RemindWin remW = edit.remind();
			switch (whn) {
			case "Today":
				remW.pickDate();
				remW.remToday();
				edit = remW.save();
				break;
			case "Tomorrow":
				remW.pickDate();
				remW.remTmrrw();
				edit = remW.save();
				break;
			case "Next Week":
				remW.pickDate();
				remW.remWk();
				edit = remW.save();
				break;
			default:
				edit = remW.cancel();
			}
		}
		first = edit.goBack();
		// verify that note appears on StickyBoard
		if (first.findNote(title))
			System.out.println("Note was successfully added to the Keep app");
		else
			System.out.println("Did not succeed in adding Note to the Keep app");
	}
}
