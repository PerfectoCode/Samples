package com.perfecto.tests.calc;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.perfecto.commons.testng.TestCaseInputs;
import com.perfecto.commons.testng.TestCases;
import com.perfecto.commons.testng.TestNGDataProvider;
import com.perfecto.pom.PrimaryCardScreen;

/*
 * Extend TestCases class to qualify class as Test
 */


public class PercentageCalculationTest2 extends TestCases{
	
	TestCaseInputs inputs;
	
	// Constructor for PercentageCalculationTest2 test. Required else test will not be picked.
	@Factory(dataProviderClass = TestNGDataProvider.class, dataProvider = TestNGDataProvider.DATAPROVIDER)
	public PercentageCalculationTest2(TestCaseInputs inputs) {
		super(inputs);
		this.inputs=inputs;
	}

	private static final long serialVersionUID = 1L;
	
	/*
	 * Set up for Test Case.
	 * Required for TestNG Listener calls
	 */

	@BeforeMethod(groups = { "anothertest" })
	public void setUpMyTest() {
		// Any Setup steps to be performed.
	}

	/*
	 * Test Case Steps
	 */

	@Test(groups = {
			"anothertest" }, description = "Another Test")
	public void myTest() {
		
		System.out.println(inputs.getTestData().get("UserID"));
		
		PrimaryCardScreen primaryCardScreen = new PrimaryCardScreen(getDriver());
		
		// Assert Enter Percentage Text Box is Displayed.
		primaryCardScreen.assertEnterPercentageTextBoxIsDisplayed();
		
		// Assert Enter Percentage Text Box is Enabled.
		primaryCardScreen.assertEnterPercentageTextBoxIsEnabled();

		// Assert Enter Percentage Text Box is Displayed.
		primaryCardScreen.assertEnterNumberTextBoxIsDisplayed();
		
		// Assert Enter Number Text Box is Enabled.
		primaryCardScreen.assertEnterNumberTextBoxIsEnabled();
		
		// Assert Result Label is Displayed.
		primaryCardScreen.assertResultLabelIsDisplayed();
		
		// Assert Result Label is Disabled.
		primaryCardScreen.assertResultLabelIsDisabled();
		
		primaryCardScreen.hideKeyboard();
		
		// Assert Calc Button is Displayed
		primaryCardScreen.assertCalcButtonIsDisplayed();
		
		// Assert Calc Button is Enabled
		primaryCardScreen.assertCalcButtonIsEnabled();
		
	}

	/*
	 * Tear down for Test Case.
	 * Required for TestNG Listener calls
	 */

	@AfterMethod(groups = { "anothertest" }, alwaysRun = true)
	public void cleanUpMyTest() {
		// Any Tear down steps to be performed.
	}

}
