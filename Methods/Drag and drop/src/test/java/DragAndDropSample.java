
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
	
public class DragAndDropSample {
	
	WebDriver driver;
	final static String host = "My_Host.perfectomobile.com";
	final static String user = "My_User";
	final static String pass = "My_Pass";
	
    @BeforeMethod
	@Parameters({"platformName" , "platformVersion" , "browserVersion" , "browserName"})
    public void beforeMethod(String platformName,String platformVersion,String browserVersion,String browserName ) throws MalformedURLException {
    	DesiredCapabilities capabilities = new DesiredCapabilities();
    	capabilities.setCapability("platformName", platformName);
    	capabilities.setCapability("platformVersion", platformVersion);
    	capabilities.setCapability("browserVersion", browserVersion);
    	capabilities.setCapability("browserName", browserName);
		capabilities.setCapability("user", user);
		capabilities.setCapability("password", pass);
		
	  	driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub") , capabilities);
	  	driver.manage().window().maximize();
	  	driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    
    @Test
    public void f() {
    	try{
    		driver.get("http://marcojakob.github.io/dart-dnd/basic/web/");
    		WebElement toDrop = driver.findElement(By.xpath("//*[@class = 'trash']"));
    		WebElement toDrag = driver.findElement(By.xpath("//*[@class = 'document'][1]"));
    		DragAndDrop(toDrag, toDrop);
    		System.out.println("Droped element 1.");
    		//Trash (drop item) changing form after the first drag and drop operation.
    		Thread.sleep(1000);
    		toDrop = driver.findElement(By.xpath("//*[@class = 'trash full']")); 
    		for(int i = 0 ; i < 3; i ++){
    			toDrag = driver.findElement(By.xpath("//*[@class = 'document'][1]"));
    			DragAndDrop(toDrag, toDrop);
    			System.out.println("Droped element "+(i+2)+".");
    			Thread.sleep(1000);
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * Drag and drop method implementation.
     * @param toDrag a web element to drag.
     * @param toDrop a web element to drop into the 'toDrag' element.
     */
    private void DragAndDrop(WebElement toDrag ,WebElement toDrop){
    	try{
	    	 Actions builder = new Actions(driver);
	    	    builder.keyDown(Keys.CONTROL)
	    	        .click(toDrag)
	    	        .dragAndDrop(toDrag, toDrop)
	    	        .keyUp(Keys.CONTROL);
	
	    	        Action selected = builder.build();
	    	        selected.perform();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    @AfterMethod
    public void afterMethod() {
    	try{
    		driver.close();
    		driver.quit();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

}
