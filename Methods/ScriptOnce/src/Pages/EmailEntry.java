package Pages;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class EmailEntry extends noomPage {
	private By header = By.xpath("//*[@text='My Noom account' or @label='My Noom account']");
	private By emailAdr = By.xpath("//*[@resource-id='com.wsl.noom:id/sign_up_email_button' or @name='log_in_with_email']");
	private By faceBook = By.xpath("//*[@resource-id='com.wsl.noom:id/sign_up_facebook_button' or @name='log_in_with_facebook']");
	// Google+ only available from Android!!
	private By googleP = By.xpath("//*[@resource-id='com.wsl.noom:id/google_plus_log_in_button']");

	public EmailEntry(AppiumDriver<MobileElement> d, String os) {
		super(d, os);
		
		try {
			d.findElement(header);
		} catch (Exception e) {
			throw e;
		}
	}
}
