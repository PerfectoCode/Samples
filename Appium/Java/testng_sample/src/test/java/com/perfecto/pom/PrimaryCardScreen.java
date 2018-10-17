package com.perfecto.pom;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.perfecto.mobile.utils.MobileScreen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class PrimaryCardScreen implements MobileScreen {
	
	AppiumDriver<MobileElement> driver;
	
//	=================================================================================================================================
	
	@AndroidFindBy(uiAutomator="UiSelector().description(\"calc\")")
	MobileElement appHeader;
		
	public void assertAppHeaderIsDisplayed() {
		Assert.assertTrue(appHeader.isDisplayed(),"App Header on Primary Card is not Visible");
	}

//	=================================================================================================================================
	
	@AndroidFindBy(id="com.perfectomobile.calc:id/percentage")
	MobileElement enterPercentageTextBx;
	
	public void assertEnterPercentageTextBoxIsDisplayed() {
		Assert.assertTrue(enterPercentageTextBx.isDisplayed(),"Enter Percentage Text Box on Primary Card is not Visible");
	}
	
	public void assertEnterPercentageTextBoxIsEnabled() {
		Assert.assertTrue(enterPercentageTextBx.isEnabled(),"Enter Percentage Text Box on Primary Card is disabled");
	}
	
	public void setPercentage(String percentage) {
		enterPercentageTextBx.sendKeys(percentage);
	}
	
//	=================================================================================================================================
	
	@AndroidFindBy(id="com.perfectomobile.calc:id/number")
	MobileElement enterNumberTextBx;
	
	public void assertEnterNumberTextBoxIsDisplayed() {
		Assert.assertTrue(enterNumberTextBx.isDisplayed(),"Enter Number Text Box on Primary Card is not Visible");
	}
	
	public void assertEnterNumberTextBoxIsEnabled() {
		Assert.assertTrue(enterNumberTextBx.isEnabled(),"Enter Number Text Box on Primary Card is disabled");
	}
	
	public void setNumber(String number) {
		enterPercentageTextBx.sendKeys(number);
	}

//	=================================================================================================================================
	
	@AndroidFindBy(id="com.perfectomobile.calc:id/results")
	MobileElement resultLabel;
	
	public void assertResultLabelIsDisplayed() {
		Assert.assertTrue(resultLabel.isDisplayed(),"Result Label on Primary Card is not Visible");
	}
	
	public void assertResultLabelIsDisabled() {
		Assert.assertFalse(resultLabel.isEnabled(),"Result Label on Primary Card is Enabled");
	}
	
	public void checkResult(String expectedNumber) {
		String actualNumber = resultLabel.getText();
		Assert.assertEquals(actualNumber, expectedNumber,String.format("Expected Number %s, Actaul Number displayed %s",expectedNumber,actualNumber));
	}

//	=================================================================================================================================
	
	@AndroidFindBy(id="com.perfectomobile.calc:id/CalcBT")
	MobileElement calcButton;
	
	public void assertCalcButtonIsDisplayed() {
		Assert.assertTrue(calcButton.isDisplayed(),"Enter Number Text Box on Primary Card is not Visible");
	}
	
	public void assertCalcButtonIsEnabled() {
		Assert.assertTrue(calcButton.isEnabled(),"Enter Number Text Box on Primary Card is disabled");
	}
	
	public void clickCalcButton() {
		calcButton.click();
	}
	
//	=================================================================================================================================
	
	public void hideKeyboard() {
		if(driver instanceof AndroidDriver<?>) {
			((AndroidDriver<MobileElement>)driver).hideKeyboard();
		}
	}
		
//	=================================================================================================================================
	
	public PrimaryCardScreen(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

}
