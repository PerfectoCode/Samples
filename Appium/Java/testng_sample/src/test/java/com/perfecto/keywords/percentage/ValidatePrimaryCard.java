package com.perfecto.keywords.percentage;

import com.perfecto.commons.testng.Keyword;
import com.perfecto.commons.testng.TestCases;
import com.perfecto.pom.PrimaryCardScreen;

/* 
 * Extend Keyword Class to Qualify this class as Keyword.
 * Implement execute method to define steps.
 * 
 */

public class ValidatePrimaryCard extends Keyword {

	private TestCases currentTestCase;

	/*
	 * Keyword Constructor
	 * Arguments:
	 * 		TestCases testCase - Represents the calling test case.
	 */
	
	public ValidatePrimaryCard(TestCases testCase) {
		
		// Call Keyword class constructor with the test case object and 
		// Step name - is used by digital zoom reporting to define step name.
		
		super(testCase, "Validate Primary Card");
		this.currentTestCase = testCase;
	}

	@Override
	public void execute() {

		PrimaryCardScreen primaryCardScreen = new PrimaryCardScreen(currentTestCase.getDriver());

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

}
