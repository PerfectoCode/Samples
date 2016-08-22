import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class IMDbTest {
	
	@SuppressWarnings("rawtypes")
	private AppiumDriver driver;
	private desktopTest desktopDriver;
	
	//Test Parameters.
	static String AppEmail			= "YourAppEmail";
	static String AppPassword		= "YourAppPassword";
	static String MovieName			= "X-Men: Apocalypse (2016)"; //Movie to rate.
	
	@Parameters({"platformName" , "model" , "deviceName"})
	@BeforeTest
	public void beforTest(String platformName , String model , String deviceName)
	{
		try{
			driver = driverCreator.createAndroidDriver(platformName, model , deviceName);
			driver.context("NATIVE_APP");
			driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	@Test
	public void TestCase() {
		try{
			//Mobile test.
			openApp();
			SignIn();
			search();
			rateMovie();
			
			//Set up a desktop test.
			desktopDriver = new desktopTest();
			desktopDriver.logIn();
			desktopDriver.search();
			desktopDriver.deleteRating();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	
	/****************
	 * Test methods *
	 ****************/
	
	//Opens the application and handles Allow location pop-up
	private void openApp(){
		HashMap<String , Object> script = new HashMap<String, Object>();
		script.put("name", "IMDb");
		driver.executeScript("mobile:application:open", script);
		//Handle Allow location Popup - allow.
		driver.findElement(By.xpath(AppLocators.AllowLocation)).click();
	}
	
	//Sign into the application with the given user name and password at the test parameters.
	private void SignIn() {		
		//First press removes the secondary menu which pops up right after opening the app.
		driver.findElement(By.xpath(AppLocators.moreOptionsButton)).click();
		driver.findElement(By.xpath(AppLocators.moreOptionsButton)).click();
		//Sign in with the IMDb account.
		driver.findElement(By.xpath(AppLocators.SignInMenuButton)).click();
		driver.findElement(By.xpath(AppLocators.SignInIMDbAccount)).click();
		//Insert personal app information.
		driver.findElement(By.xpath(AppLocators.AppEmail)).click();
		driver.findElement(By.xpath(AppLocators.AppEmail)).sendKeys(AppEmail);
		driver.findElement(By.xpath(AppLocators.AppPassword)).click();
		driver.findElement(By.xpath(AppLocators.AppPassword)).sendKeys(AppPassword);
		driver.findElement(By.xpath(AppLocators.SignInButton)).click();
	}
	
	@SuppressWarnings("rawtypes")
	private void search(){
		//Searching the movie name.
		driver.findElement(By.xpath(AppLocators.Search)).click();
		driver.findElement(By.xpath(AppLocators.SearchBox)).sendKeys(MovieName);
		//Click enter/search on the android device keyboard.
		((AndroidDriver)driver).sendKeyEvent(AndroidKeyCode.ENTER);
		driver.findElement(By.xpath(AppLocators.firstSearchResult)).click();
	}
	
	private void rateMovie(){
		driver.findElement(By.xpath(AppLocators.RateThisButton)).click();
		driver.findElement(By.xpath(AppLocators.StartsButton)).click();
		driver.findElement(By.xpath(AppLocators.SaveRating)).click();
	}
		
	@AfterTest
	public void afterTest(){
		try{
			desktopDriver.finishDesktopSession();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			driver.close();
			driver.quit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
