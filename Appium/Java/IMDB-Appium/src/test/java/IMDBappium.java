package test.java;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.ios.IOSDriver;

public class IMDBappium {
	
	AppiumDriver 	driver;
	WebDriverWait	wait;
	String 			os;
	final int 		interval = 30000; // The interval of time to watch the trailer. *currently 30 seconds.
	
	//TODO: Change my user , password and host cloud URL.
	final String user = "My_User";
	final String pass = "My_Pass";
	final String host = "My_Host.perfectomobile.com";
	
	
	@Parameters({"deviceName" , "platformName" })
	@BeforeTest
	public void beforTest(String deviceName , String platformName) throws MalformedURLException{

		this.os = platformName;
	
		try{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("user", user);
			capabilities.setCapability("password", pass);
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("platformName", platformName);
			capabilities.setCapability("autoAcceptAlerts",true); 
			
			
			if(os.equals("iOS")){
				driver = new IOSDriver(new URL("https://" + this.host + "/nexperience/perfectomobile/wd/hub"), capabilities) ;
			}
			else{
				driver = new AndroidDriver(new URL("https://" + this.host + "/nexperience/perfectomobile/wd/hub"), capabilities);
			}
			
			wait = new WebDriverWait(driver, 10);
			driver.context("NATIVE_APP");
			driver.manage().timeouts().implicitlyWait(15 , TimeUnit.SECONDS);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void f() {
		System.out.println("Starting test on platform: " + os);
		try{
			OpenAPP();
			Search();
			if(os.equals("Android")) AndroidWatchTrailer() ;
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Opens the application.
	 * TODO: complete Android/iOS if-else cases in order to download the application
	 * 		 before starting the test.
	 * Also possible to set the capabilities : capabilities.setCapability("app", "PRIVATE:AppPath");
	 */
	public void OpenAPP(){
		if(os.equals("Android")){
			//enable in order to install the application from your cloud's repository.
			//driver.installApp("PRIVATE:IMDb Movies TV_v6.1.4.106140300_apkpure.com.apk");
		}
		else{
			//download on IOS case.
		}
		
		HashMap<String , Object> script = new HashMap<String, Object>();
		script.put("name", "IMDb");
		driver.executeScript("mobile:application:open", script);
	}
	
	/**
	 * Search for the movie captain america in imdb application.
	 * @throws InterruptedException 
	 */
	public void Search() throws InterruptedException{
		//Search bar.
		driver.findElement(By.xpath(AppObjects.Search)).click();
		
		//Search result (1st).
		if(os.equals("Android")){
			driver.findElement(By.xpath(AppObjects.Android_search_bar)).sendKeys("Captain America Civil War");
			//There is no built in search key in the application , use android's native keyboard.
			((AndroidDriver)driver).sendKeyEvent(AndroidKeyCode.ENTER);
			driver.findElement(By.xpath(AppObjects.MoviePath)).click();
		}
		else{
			driver.getKeyboard().sendKeys("Captain America Civil War");
			driver.findElement(By.xpath(AppObjects.IOSWatchTrailer)).click();
			Thread.sleep(this.interval); // Time interval to sleep while playing the trailer.
		}
		
	}
	
	/**
	 * ScrollTo Rate this movie button.
	 */
	public void AndroidWatchTrailer(){
		try{
			String scrollPoint = "Play Trailer";
			
			((AndroidDriver)driver).findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0))"
					+ ".scrollIntoView(new UiSelector().textContains(\""+scrollPoint+"\").instance(0))").click();
			
			Thread.sleep(this.interval); // Time interval to sleep while playing the trailer.

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@AfterTest
	public void afterTest(){
		try{
			HashMap<String , Object> script = new HashMap<String, Object>();
			script.put("name", "IMDb");
			driver.executeScript("mobile:application:close", script);

		}catch(Exception e){
			System.out.println("Could not close the application.");
		}
		
		try{
			driver.close();
			driver.quit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
  
}
