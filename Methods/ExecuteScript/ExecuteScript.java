import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ExecutingJS_Java {

	static RemoteWebDriver driver;
	
	//TODO: Set your Perfecto lab User ,Pass and Host.
	static String user = "MY_USER";
	static String pass = "MY_PASS";
	static String host = "MY_HOST.perfectomobile.com";
	
	//Initialize driver
	public static RemoteWebDriver InitDriver() throws MalformedURLException{
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("user", user);
		capabilities.setCapability("password", pass);
		
		//TODO: Set your device capabilities here:
		capabilities.setCapability("platformName", "Windows");
		capabilities.setCapability("platformVersion", "8.1");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("browserVersion", "50");
		
		return new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub") , capabilities);
	}
	
	public static void main(String[] args) {
		try{
			driver = InitDriver();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			//Navigate to Amazon.com using JavaScript
			driver.executeScript("window.location.href = \"https://www.amazon.com/\";");
			
			//verify page title
			if(!driver.getTitle().equals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more")){
				//if titles not equals then throw exception.
				throw new Exception("Page title are not equals! ");
			}
			
			//Insert text using JavaScript
			driver.executeScript("document.getElementById(\"nav-link-yourAccount\").click();");
			driver.executeScript("document.getElementById(\"createAccountSubmit\").click();");
			driver.executeScript("document.getElementById(\"ap_customer_name\").value = \"Testing name\";");
			driver.executeScript("document.getElementById(\"ap_email\").value = \"Testing_mail@mailservice.com\";");
			driver.executeScript("document.getElementById(\"ap_password\").value = \"passTest123\";");
			driver.executeScript("document.getElementById(\"ap_password_check\").value = \"passTest123\";");
			driver.executeScript("document.getElementById(\"continue\").click()");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally {
			driver.close();
			driver.quit();
		}
	
	}
}
