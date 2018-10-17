package com.perfecto.tests.calc;

import org.openqa.selenium.Point;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.perfecto.commons.testng.TestCaseInputs;
import com.perfecto.commons.testng.TestCases;
import com.perfecto.commons.testng.TestNGDataProvider;
import com.perfecto.pom.LaunchScreen;

/*
 * Extend TestCases class to qualify class as Test
 */

public class RotateScreenTest extends TestCases{

	
	// Constructor for Rotatescreen test. Required else test will not be picked.
	@Factory(dataProviderClass = TestNGDataProvider.class, dataProvider = TestNGDataProvider.DATAPROVIDER)
	public RotateScreenTest(TestCaseInputs inputs) {
		super(inputs);
	}

	private static final long serialVersionUID = 1L;
	
	/*
	 * Set up for Test Case.
	 * Required for TestNG Listener calls
	 */
	
	@BeforeMethod(groups = { "rotate_screen" })
	public void setUpMyTest() {
		// Any Setup steps to be performed.
	}

	/*
	 * Test Case Steps
	 */
	
	@Test(groups = {
			"rotate_screen" }, description = "Rotate Screen")
	public void myTest() {
		
		// Initialize Screen Object
		LaunchScreen launchScreen =new LaunchScreen(getDriver());
		
		
		// Get Env Element location
		Point location = launchScreen.getEnvLocation();

		// Click on Arg Button
		launchScreen.clickArg();
		
		System.out.println("----- Before Rotation -------");
		
		// Rotate Device
		launchScreen.rotateScreen();
		
		// Get Env Element location
		location = launchScreen.getEnvLocation();
		
		System.out.println("----- After Rotation -------");
		
		// Click Env Element based on Coordinate
		launchScreen.clickEnvByCoordinate(location);
		
	}
	
	/*
	 * Tear down for Test Case.
	 * Required for TestNG Listener calls
	 */

	@AfterMethod(groups = { "rotate_screen" }, alwaysRun = true)
	public void cleanUpMyTest() {
		// Any Tear down steps to be performed.
	}

}
