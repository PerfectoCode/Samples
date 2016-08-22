import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class desktopTest{
	
	RemoteWebDriver driver;
	WebDriverWait	wait;
	
	public desktopTest() throws MalformedURLException{
		try{
			driver = driverCreator.desktopDriver();
			driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get("http://www.imdb.com/");
			wait = new WebDriverWait(driver, 25);
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public void logIn(){
		try{
			driver.findElement(By.xpath(AppLocators.webLogIn)).click();
			driver.findElement(By.xpath(AppLocators.WebIMDbLogIn)).click();
			driver.findElement(By.xpath(AppLocators.webEmail)).sendKeys(IMDbTest.AppEmail);;
			driver.findElement(By.xpath(AppLocators.webPass)).sendKeys(IMDbTest.AppPassword);
			driver.findElement(By.xpath(AppLocators.webSubmitButton)).click();
			
		}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	public void search(){
		try{
			driver.findElement(By.xpath(AppLocators.SearchBarweb)).sendKeys(IMDbTest.MovieName);
			driver.findElement(By.xpath(AppLocators.SearchButtonWeb)).click(); 
			driver.findElement(By.xpath(AppLocators.FirstWebResult)).click();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteRating(){
		try{
			driver.navigate().refresh(); 
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(AppLocators.webYourRating))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(AppLocators.webDeleteRating))).click();
		}catch(Exception e){ 
			e.printStackTrace();
		}
	}
	
	public void finishDesktopSession(){
		driver.close();
		driver.quit();
	}
	
}
