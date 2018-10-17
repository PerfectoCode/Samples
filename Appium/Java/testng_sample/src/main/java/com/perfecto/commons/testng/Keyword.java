package com.perfecto.commons.testng;

public abstract class Keyword {

	public Keyword(TestCases testCase,String keywordDescription) {
		
		if(testCase.closeStep()) {
			testCase.EndStep();
		}
		
		testCase.StartStep(keywordDescription);
	}
	
	public abstract void execute();
	
}
