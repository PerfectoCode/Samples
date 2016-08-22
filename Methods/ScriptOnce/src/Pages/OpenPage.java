package Pages;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class OpenPage extends noomPage {

	private By getStart = By.xpath("//*[@label='Get started' or @text='Get started']");
	private By welcomeImage = By.xpath("/*[@name='coach_welcome_image']");
	public OpenPage(AppiumDriver<MobileElement> d, String os){
		super(d, os);
		// verify that we are starting the application
		// look for Get started button
		try {
			driver.findElement(getStart);
		} catch (Exception e) {
			System.out.println("Failed to start the application from beginning");
		}
	}
	
	public CreateAccountPage getStarted() {
		driver.findElement(getStart).click();
		
		return new CreateAccountPage(driver, opS.toString());
	}
}
