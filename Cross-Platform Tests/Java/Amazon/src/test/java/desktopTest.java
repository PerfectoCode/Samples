import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;

/** 
 * @author Daniel.
 * Selenium web driver test.
 * This test continues the mobile Test.
 */
public class desktopTest {

	private RemoteWebDriver driver;
	
	@BeforeTest
	public void beforeTest() throws MalformedURLException{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		System.setProperty("webdriver.chrome.driver", "c:/selenium/chromedriver.exe");
		capabilities.setCapability("user", Properties.user);
		capabilities.setCapability("password", Properties.pass);
		capabilities.setCapability("platformName", "Windows");
		capabilities.setCapability("platformVersion", "8.1");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("browserVersion", "49");
		
		driver = new RemoteWebDriver(new cloud().getwebURL() , capabilities);
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	//Starting the web session and login to Amazon.co.uk
	public void StartAndLogIn(){
		try{
			driver.get(Properties.Website);
			driver.findElement(By.id(TestObjects.desktop_login_id)).click();
			driver.findElement(By.id(TestObjects.desktop_username_id)).sendKeys(Properties.AppUser);
			driver.findElement(By.id(TestObjects.desktop_password_id)).sendKeys(Properties.AppPass);
			driver.findElement(By.id(TestObjects.desktop_loginBTN_id)).click();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//Clicking on cart and fill the checkout page.
	public void CartAndCheckout(){
		try{
			driver.findElement(By.id(TestObjects.desktop_cart_id)).click();
			driver.findElement(By.xpath(TestObjects.desktop_checkout_xpath)).click();
			//fill delivery address.
			driver.findElement(By.id(TestObjects.desktop_full_name_id)).sendKeys(Properties.FullName);
			driver.findElement(By.id(TestObjects.desktop_address_line1_id)).sendKeys(Properties.AddressLine1);
			driver.findElement(By.id(TestObjects.dekstop_city_id)).sendKeys(Properties.Town);
			driver.findElement(By.id(TestObjects.desktop_country1_id)).sendKeys(Properties.Country);
			driver.findElement(By.id(TestObjects.desktop_post_code_id)).sendKeys(Properties.PostCode);
			//Country select type.
			new Select(driver.findElement(By.id(TestObjects.desktop_CountrySelect_id))).selectByVisibleText("Israel");
			
			driver.findElement(By.id(TestObjects.desktop_phone_id)).sendKeys(Properties.Phone);
			new Select(driver.findElement(By.id(TestObjects.desktop_address_type_id))).selectByValue("COM");
			driver.findElement(By.xpath(TestObjects.desktop_Continue_xpath)).click();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//Delete items from the basket.
	public void CleanBasket(){
		try{
			driver.get(Properties.Website);
			driver.findElement(By.id(TestObjects.desktop_cart_id)).click();
			driver.findElement(By.xpath(TestObjects.desktop_deleteItem_xpath)).click();
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	//Closing the driver.
	public void CloseDriver(){
		driver.close();
		driver.quit();
	}
	
	
}
