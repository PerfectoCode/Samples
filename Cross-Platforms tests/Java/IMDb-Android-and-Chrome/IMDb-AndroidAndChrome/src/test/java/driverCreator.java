import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class driverCreator{
	
	private static String cloudUser 		= "Your_User";
	private static String cloudPass 		= "Your_Pass";
	private static String cloudHost 		= "YourHost.perfectomobile.com";
	private static String AppPathAndroid	= "App installetion path on cloud repository";
	
	//Creating AndroidDriver (Appium).
	@SuppressWarnings("rawtypes")
	public static AndroidDriver createAndroidDriver(String platformName , String model , String deviceName) throws MalformedURLException{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		//User capabilities
		capabilities.setCapability("user", cloudUser);
		capabilities.setCapability("password", cloudPass);
		
		//Device capabilities
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("model", model);
		
		//App capabilities
		capabilities.setCapability("fullReset",true);  
		capabilities.setCapability("app",AppPathAndroid);
		
		return new AndroidDriver(getCloud(), capabilities);
	}
	
	//Creating IOSDriver (Appium).
	//The app should be already installed on the iOS device! .
	@SuppressWarnings("rawtypes")
	public static IOSDriver createIOSDriver(String platformName , String model) throws MalformedURLException{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		//User capabilities
		capabilities.setCapability("user", cloudUser);
		capabilities.setCapability("password", cloudPass);
		
		//Device capabilities
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("model", model);
		
		return new IOSDriver(getCloud(), capabilities);
	}
	
	//Creating a desktop driver .
	public static RemoteWebDriver desktopDriver() throws MalformedURLException{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		//User capabilities
		capabilities.setCapability("user", cloudUser);
		capabilities.setCapability("password", cloudPass);
		
		capabilities.setCapability("platformName", "Windows");
		capabilities.setCapability("platformVersion", "8.1");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("browserVersion", "49");
		
		return new RemoteWebDriver(getCloud() , capabilities);
		
	}
	
	//Returns the cloud as a URL.
	public static URL getCloud() throws MalformedURLException{
		return new URL("https://" + cloudHost + "/nexperience/perfectomobile/wd/hub");
	}
	
}