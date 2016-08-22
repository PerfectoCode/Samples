package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class WeightGoalPage extends noomPage {
	private By kg = By.xpath("//*[@name='kilograms']");
	private By lb = By.xpath("//*[@name='pounds']");
	private By five = By.xpath("//UIAStaticText[@name='5']");
	private By ten = By.xpath("//UIAStaticText[@name='10']");
	private By fifteen = By.xpath("//UIAStaticText[@name='15']");
	private By twenty = By.xpath("//UIAStaticText[@name='20']");
	private By goalBtn = By.xpath("//*[@name='weight_button']");
	
	public WeightGoalPage(AppiumDriver<MobileElement> d, String os) {
		super(d, os);
		
		switch (opS) {
		case Android:
			throw new NoSuchElementException("Android does not present the weight goal page!");
		case iOS:
			driver.findElement(goalBtn);
		}
	}
	
	public void selectGoal(int goal) {
		if (goal < 7) {
			driver.findElement(five).click();
		} else if (goal < 12) {
			driver.findElement(ten).click();
		} else if (goal < 17) {
			driver.findElement(fifteen).click();
		} else {
			driver.findElement(twenty).click();
		}
	}
	
	public void pickUnits(String unit) {
		if (unit.equalsIgnoreCase("kg")) {
			driver.findElement(kg).click();
		} else {
			driver.findElement(lb).click();
		}
	}
	
	public UserDetailsPage setGoal() {
		driver.findElement(goalBtn).click();
		
		return new UserDetailsPage(driver, opS.toString());
	}
}
