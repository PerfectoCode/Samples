package Reporting.Pages;

import org.openqa.selenium.By;

import com.perfecto.reportium.client.ReportiumClient;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public abstract class KeepPage {
	AndroidDriver<MobileElement> driver;
	ReportiumClient rep;
	
	private By getStarted = By.xpath("//android.widget.TextView[@text='GET STARTED']");

	public KeepPage(AndroidDriver<MobileElement> d, ReportiumClient r) {
		this.driver = d;
		this.rep = r;
	}
	
	protected void dismissPopup() {
		try {
			driver.findElement(getStarted).click();
			System.out.println("Pressed the Get Started button to get into the application");
		} catch (Exception n) {
			System.out.println("No Welcome screen - just plow ahead");
		}
	}
}
