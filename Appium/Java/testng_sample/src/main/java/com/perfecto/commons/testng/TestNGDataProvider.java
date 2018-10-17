package com.perfecto.commons.testng;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.DataProvider;

import com.perfecto.commons.utils.GenerateTestData;

public class TestNGDataProvider {
	
	public final static String DATAPROVIDER="DataProvider";
	
	@DataProvider(name=DATAPROVIDER,parallel=true)
	public static Object[][] getTestData(ITestContext test, ITestNGMethod methos){

		return GenerateTestData.getData(test.getCurrentXmlTest().getIncludedGroups().get(0),test.getCurrentXmlTest().getAllParameters());
	}
}
