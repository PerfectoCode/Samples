package com.perfecto.pom;

import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.support.PageFactory;

import com.perfecto.mobile.utils.MobileScreen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

/*
 * Implement MobileScreen Interface to qualify the class as a Mobile Screen class
 */

public class LaunchScreen implements MobileScreen{
	
	AppiumDriver<MobileElement> driver;
	
//	=================================================================================================================================
	
	public LaunchScreen(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
//	=================================================================================================================================
	
	@iOSFindBy(xpath="//*[@label=\"Env\"]")
	MobileElement envButton;
	
	public Point getEnvLocation() {
		return envButton.getLocation();
	}
	
	public void clickEnv() {
		envButton.click();
	}
	
	public void clickEnvByCoordinate(Point location) {
		
		TouchAction touchAction = new TouchAction(driver);
		touchAction.tap(location.getX() + 10, location.getY() + 10).perform();
	}
	
	public boolean isEnvSelected() {
		String isSelected = envButton.getAttribute("value");
		return isSelected !=null &&  isSelected.equals("1");
	}

//	=================================================================================================================================
	
	@iOSFindBy(xpath="//*[@label=\"Arg\"]")
	MobileElement argButton;
	
	
	public void clickArg() {
		argButton.click();
	}
	
	public void rotateScreen() {		
		driver.rotate(ScreenOrientation.PORTRAIT);

	}

}
