package Pages;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class CreateAccountPage extends noomPage {

	private By emailLogin = By.xpath("//*[starts-with(@text, 'Sign up with Email, Facebook') or @label='Sign up with Email or Facebook']");
	public CreateAccountPage(AppiumDriver<MobileElement> d, String os){
		super(d, os);
		// verify that we are starting the application
		// look for Get started button
		try {
			driver.findElement(emailLogin);
		} catch (Exception e) {
			System.out.println("Failed to display ");
		}
	}

	public noomPage startAcct() {
		driver.findElement(emailLogin).click();
		
		switch (opS) {
		case Android:
		default:
			return new UserDetailsPage(driver, "Android");
		case iOS: 
			return new WeightGoalPage(driver, "iOS");
		}
	}
}
