
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

/**
 * @author Daniel
 * Hybrid test , Mobile test and desktop website.
 * Tested on amazon.co.uk website and amazon app (uk ver).
 */
public class mobileTest {

	@SuppressWarnings("rawtypes")
	private AppiumDriver 	driver;
	private WebDriverWait 	wait; 		//Appium driver wait.
	private String 			os;			//Device operation system / Platform
	private desktopTest 	desktop; 	//desktop test class.
	
	private static final String nativeAPP 	= "NATIVE_APP"; //nativeAPP context 
	private boolean LoggedIn 				= false; 		//user is logged in.
	
	@SuppressWarnings("rawtypes")
	@Parameters({"platformName" , "model" , "deviceName"})
	@BeforeTest
	public void beforeTest(String platformName , String model , String deviceName) throws IOException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("user", Properties.user);
		capabilities.setCapability("password", Properties.pass);
		
		//Set this capabilities from the ".XML" file:
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("model", model);
		capabilities.setCapability("deviceName", deviceName);
		
		os = platformName;
		if(os.equals("iOS")){
			driver = new IOSDriver(new cloud().getmobileURL(), capabilities);
		}else{
			driver = new AndroidDriver(new cloud().getmobileURL(), capabilities);
		}
		driver.manage().timeouts().implicitlyWait(10 , TimeUnit.SECONDS);
		driver.context(nativeAPP); // Stating test in nativeAPP context.
		wait = new WebDriverWait(driver, 15); 
	}
	
	@Test
  	public void f() {
		boolean mobileSucceed = false;
		try{
			System.out.println("Starting mobile test.");
		  	openAPP();
		  	loginAPP();
		  	search();
		  	mobileSucceed = true;
		}catch(Exception e){ System.out.println(e);}
		
		if(LoggedIn) signOut();
		if(mobileSucceed){
			try{
				System.out.println("Starting desktop test.");
				StartDesktopSession();
				desktop.StartAndLogIn();
				desktop.CartAndCheckout();
				//desktop.CleanBasket(); cleaning basket not necessary.
				desktop.CloseDriver();
			}catch(Exception e){ System.out.println(e);}
		}
  	}

  	@AfterTest
  	public void afterTest() {
  		try{
  			closeAPP();
  			driver.close();
  			driver.quit();
  		}catch(Exception e){
  			System.out.println(e);
  		}
  		
  	}

  	//Open application method.
	private void openAPP(){
		if(driver.getContext().equals(nativeAPP)){
			HashMap<String, Object> script = new HashMap<String, Object>();
			if(os.equals("iOS")){
				script.put("name", Properties.AppNameiOS);
			}
			else{
				script.put("name", Properties.AppNameAndroid);
			}
			driver.executeScript("mobile:application:open", script);
		}
	}
	
	//Login method. 
	private void loginAPP(){
		try{
			if(os.equals("iOS")){
				wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(TestObjects.Sign_in)))).click();
				driver.findElement(By.xpath(TestObjects.userName)).sendKeys(Properties.AppUser);
				driver.findElement(By.xpath(TestObjects.password)).sendKeys(Properties.AppPass);
				driver.findElement(By.xpath(TestObjects.login_button)).click();
			}
			else{//Android require click on the fields.
				wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(TestObjects.Sign_in)))).click();
				driver.findElement(By.xpath(TestObjects.userName)).click();
				driver.findElement(By.xpath(TestObjects.userName)).sendKeys(Properties.AppUser);
				driver.findElement(By.xpath(TestObjects.password)).click();
				driver.findElement(By.xpath(TestObjects.password)).sendKeys(Properties.AppPass);
				driver.findElement(By.xpath(TestObjects.Android_loginBTN)).click();
			}
			
			this.LoggedIn = true;
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//Search method.
	private void search(){
		boolean menu_loaded = false;
		if(os.equals("iOS")){
			try{//Finding search area could be problematic therefore trying twice. 
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestObjects.Search))).sendKeys(Properties.SearchValue);
				menu_loaded = true;
			}catch(Exception e){}
			if(!menu_loaded){
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestObjects.Search))).sendKeys(Properties.SearchValue);
			}
		}
		else{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestObjects.Search))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(TestObjects.AndroidSearch2))).sendKeys(Properties.SearchValue);
		}
		
		driver.findElement(By.xpath(TestObjects.CDsAndVinyl)).click();
		driver.findElement(By.xpath(TestObjects.DarkSideCD)).click();
		//This part of code separate two cases of ScrollTO method.
		//In order to find Basket button scrolling down and search for the text "Add to Basket" .
		try{
			if(os.equals("iOS")){
				driver.findElementByClassName("UIAButton");
				//Scrolling down and find element by it's tag name.
				IOSElement tbl = (IOSElement) driver.findElementByClassName("UIAWebView");
				tbl.scrollTo("Add to Basket").click();
			}
			else{
					driver.scrollTo("Add to Basket");
					driver.findElement(By.xpath(TestObjects.Android_AddtoBasket)).click();
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//signOut method.
	private void signOut(){
		openAPP();//Reopen the application  in order to back the main menu.
		try{
			driver.findElement(By.xpath(TestObjects.AppMenu)).click();
			if(os.equals("iOS")){
				driver.findElement(By.xpath(TestObjects.SignOut)).click();
				driver.findElement(By.xpath(TestObjects.PopUpSignOut)).click();
			}
			else{
				driver.scrollTo("Not "+Properties.NameOnly+"? Sign out");
				driver.findElement(By.xpath("//*[@text='Not "+Properties.NameOnly+"? Sign out']")).click();
				driver.findElement(By.xpath(TestObjects.Android_PopUpSignOut)).click();
			}
			this.LoggedIn = false;
		}catch(Exception e){
			System.out.println("Sign out unsuccessfully.");
		}
	}
	
	private void closeAPP(){
		if(driver.getContext().equals(nativeAPP)){
			HashMap<String, Object> script = new HashMap<String, Object>();
			if(os.equals("iOS")){
				script.put("name", Properties.AppNameiOS);
			}
			else{
				script.put("name", Properties.AppNameAndroid);
			}
			driver.executeScript("mobile:application:close", script);
		}
	}
	
	//Setting the desktop test to be ready.
	private void StartDesktopSession() throws MalformedURLException{
		desktop = new desktopTest();
		desktop.beforeTest();
	}
}
