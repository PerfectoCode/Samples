import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class GoogleSearchTest{
	
	static RemoteWebDriver driver;
	
	//TODO: Provide your cloud's information: host, user and password.
	static String host = "My_Host.perfectomobile.com";
	static String user = "My_User";
	static String pass = "My_Pass";
	
	//Driver initializing. 
	public static void initDriver() throws MalformedURLException{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("user", user); 
		capabilities.setCapability("password", pass);
		
		//TODO: Change the capabilities.
		//Desktop capabilities for example.
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("platformVersion", "7");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "49");
        
        driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub") , capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
	}
	
	//TEST.
	public static void main(String[] args) {
		try
		{
			initDriver();
			
			//Search in google.
			driver.get("https://google.com");
			driver.findElement(By.name("q")).sendKeys("perfecto mobile"); // Finding the search bar and sends perfecto mobile.
			driver.findElement(By.xpath("//*[@id='rso']/div[1]/div[1]/div/h3/a")).click(); // Click on the first search result.
			
			//Fills a form on perfecto website.
			driver.findElement(By.xpath("//*[text() = 'Start Free']")).click();
			driver.findElement(By.id("FirstName")).sendKeys("MyFirstName");
			driver.findElement(By.id("LastName")).sendKeys("MyLaseName");
			driver.findElement(By.id("Company")).sendKeys("MyCompany");
			driver.findElement(By.id("Mobile_Testing_Role__c")).click();
			driver.findElement(By.xpath("//*[@value = 'Development']")).click();
			driver.findElement(By.id("Email")).sendKeys("MyEmail@somehost.com");
			driver.findElement(By.id("Phone")).sendKeys("123456789");
			driver.findElement(By.id("Country")).click();
			driver.findElement(By.xpath("//*[@value = 'Israel']")).click();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		EndTest();
	}
	
	//End test , closing the driver and downloads the report.
	public static void EndTest(){
		try{
			driver.close();
			PerfectoLabUtils.downloadReport(driver, "pdf", "report"); // Downloads the report using perfecto utils
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		driver.quit();
	}
	
}