package com.perfecto.tests.calc;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.perfecto.commons.testng.TestCaseInputs;
import com.perfecto.commons.testng.TestCases;
import com.perfecto.commons.testng.TestNGDataProvider;
import com.perfecto.keywords.percentage.ValidatePrimaryCard;

/*
 * Extend TestCases class to qualify class as Test
 */


public class PercentageCalculationTest extends TestCases{
	
	// Constructor for PercentageCalculationTest test. Required else test will not be picked.
	@Factory(dataProviderClass = TestNGDataProvider.class, dataProvider = TestNGDataProvider.DATAPROVIDER)
	public PercentageCalculationTest(TestCaseInputs inputs) {
		super(inputs);
	}

	private static final long serialVersionUID = 1L;
	
	/*
	 * Set up for Test Case.
	 * Required for TestNG Listener calls
	 */

	@BeforeMethod(groups = { "primarycard_validation" })
	public void setUpMyTest() {
		// Any Setup steps to be performed.
	}

	/*
	 * Test Case Steps
	 */

	@Test(groups = {
			"primarycard_validation" }, description = "Verify Primary Card Elements")
	public void myTest() {
		
		ValidatePrimaryCard validatePrimaryCardKeyword = new ValidatePrimaryCard(this);
		validatePrimaryCardKeyword.execute();
		
	}

	/*
	 * Tear down for Test Case.
	 * Required for TestNG Listener calls
	 */

	@AfterMethod(groups = { "primarycard_validation" }, alwaysRun = true)
	public void cleanUpMyTest() {
		// Any Tear down steps to be performed.
	}

}
