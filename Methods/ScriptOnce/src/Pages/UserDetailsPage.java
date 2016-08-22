package Pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class UserDetailsPage extends noomPage {
	// joint Android/iOS xpath
	private By sexF = By.xpath("//*[@label='female' or @text='Female']");
	private By sexM = By.xpath("//*[@label='male' or @text='Male']");
	// xPath for Android app
	private By ageG = By.xpath("//*[@resource-id='com.wsl.noom:id/profile_age']");
	private By firstName = By.xpath("//*[@resource-id='com.wsl.noom:id/profile_name']");
	private By nextBtn = By.xpath("//*[@resource-id='com.wsl.noom:id/action_next' or @label='Next']");
	private By htGunitFt = By.xpath("//*[@resource-id='com.wsl.noom:id/height_ft_in']");
	private By htGunitMtr = By.xpath("//*[@resource-id='com.wsl.noom:id/height_cm']");
	private By htEntry = By.xpath("//*[@resource-id='com.wsl.noom:id/profile_height']");
	// xpath for iOS app
	private By ageAbtn = By.xpath("//*[@name='profile_age']/UIAStaticText[2]");
	private By ageBtn = By.xpath("//*[@name='profile_age']/UIAStaticText[1]");
	private By wgtAbtn = By.xpath("//*[@name='profile_weight']/UIAStaticText[2]");
	private By wgtBtn = By.xpath("//*[@name='profile_weight']/UIAStaticText[1]");
	private By wgtAunitLb = By.xpath("//*[@name='pounds']");
	private By wgtAunitKg = By.xpath("//*[@name='kilograms']");
	private By htAbtn = By.xpath("//*[@name='profile_height']/UIAStaticText[2]");
	private By htTbl = By.xpath("//*[@name='profile_height']");
	private By wtTbl = By.xpath("//*[@name='profile_weight']");
	private By ageTbl = By.xpath("//*[@name='profile_age']");
	private String htBtn = "//UIAStaticText[@name='%s']";
	private String wtBtn = "//UIAStaticText[@label='%s']";
	private By htAunitFt = By.xpath("//*[@name='profile_height']/UIAStaticText[contains(@value, 'feet')]");
	private By htAunitMtr = By.xpath("//*[@name='profile_height']/UIAStaticText[@value='centimeters']");
	
	
	public UserDetailsPage(AppiumDriver<MobileElement> d, String os) {
		super(d, os);
	}
	
	public void setGender(String g) {
		if (g.equalsIgnoreCase("male")) {
			driver.findElement(sexM).click();
		} else {
			driver.findElement(sexF).click();
		}
		return;
	}
	
	public void setAge(Integer age) {
		switch (opS) {
		case Android:
			driver.findElement(ageG).sendKeys(age.toString());
			return;
		case iOS:
			Integer target;
			driver.findElement(ageBtn).click();
			// evaluate the approximate point to click on Slider.
			if ((age % 5) == 0)
				target = age;
			else
				target = ((age / 5) + 1) * 5;
			Integer ofs = (target - age) * 60;
			WebElement currAge = driver.findElement(ageAbtn);
			Integer dispAge = Integer.valueOf(currAge.getAttribute("value"));
			// use "SlideRule" to select the value
			selectSlider(dispAge, target, ofs, driver.findElement(ageTbl),
					htBtn, 10, 5);
			return;
		}
	}
	
	public void enterName(String name) {
		// only the Android app requires that you enter the first name on details
		if (opS == OpSystem.Android)
			driver.findElement(firstName).sendKeys(name);
	}
	
	public void enterHt (Integer ht, String unit) {
		WebElement currHt;
		Integer extreme = 0;
		switch (opS) {
		case Android:
			if (unit.equalsIgnoreCase("cm")) {
				driver.findElement(htGunitMtr).click();
			} else {
				driver.findElement(htGunitFt).click();
			}
			driver.findElement(htEntry).sendKeys(ht.toString());
			return;
		case iOS:
			driver.findElement(htTbl).click();
			// Select the units for the height
			if (unit.equalsIgnoreCase("cm")) {
				driver.findElement(htAunitMtr).click();
			} else {
				driver.findElement(htAunitFt).click();
			}
			// retrieve the current value displayed on the slider
			currHt = driver.findElement(htAbtn);
			String dispHtS = currHt.getAttribute("label");
			Integer dispHt = Integer.valueOf(dispHtS.substring(0, dispHtS.indexOf(' ')));
			// compute where to click on the slider 
			Integer target;
			if ((ht % 10) == 0)
				target = ht;
			else
				target = ((ht / 10) + 1) * 10;
			Integer ofs = (target - ht) * 30;
			// use "SlideRule" to select the value
			selectSlider(dispHt, target, ofs, driver.findElement(htTbl), 
					htBtn, 20, 10);
			return;
		}
	}
	
	public void enterWt (Integer wt, String unit) {
		if (opS == OpSystem.iOS) {
			Integer dispWt = 0;
			driver.findElement(wgtBtn).click();
			if (unit.equalsIgnoreCase("kg")) {
				driver.findElement(wgtAunitKg).click();
			} else {
				driver.findElement(wgtAunitLb).click();
			}
			// add code for entering the value
			WebElement currWt = driver.findElement(wgtAbtn);
			String dispWtS = currWt.getAttribute("value");
			try {
				dispWt = Integer.valueOf(dispWtS.substring(0, dispWtS.indexOf(' ')));
			} catch (Exception e) {
				dispWt = Integer.valueOf(dispWtS.substring(0, dispWtS.indexOf('.')));
			}
			// use "SlideRule" to select the value
			selectSlider(dispWt, wt, 0, driver.findElement(wtTbl), 
					wtBtn, 2, 1 );
		}
		return;
	}
	
	public EmailEntry gotoEmail() {
		driver.findElement(nextBtn).click();
		// check that we are not missing any information
		// we check by looking to see if the male button is still on the screen!
		try {
			driver.findElement(sexM);
			return null;
		} catch (Exception e) {
			return new EmailEntry(driver, opS.toString());
		}
	}
	
}
