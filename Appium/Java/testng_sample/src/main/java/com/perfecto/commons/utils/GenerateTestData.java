package com.perfecto.commons.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.perfecto.commons.testng.Logger;
import com.perfecto.commons.testng.Logger.STATUS;
import com.perfecto.commons.testng.TestCaseInputs;
import com.perfecto.commons.utils.DevicesToTestOn.Device;

public class GenerateTestData {

	public static Object[][] getData(String groupName, Map<String,String> deviceInfo){
		
		List<TestCaseInputs> testCaseInputs = getInputs(groupName, deviceInfo);
		
		Object[][] testData = new Object[testCaseInputs.size()][1];
		
		for(int index=0;index<testCaseInputs.size();++index) {
			testData[index][0] = testCaseInputs.get(index);
		}
		
		return testData;
	}
	
	private static List<TestCaseInputs> getInputs(String groupName, Map<String,String> deviceInfo){
		
		List<TestCaseInputs> testCaseInputs = new ArrayList<>();
		
		JSONArray testDatas = readJSONArray(groupName);
		
		Device deviceToTestOn = new Device(deviceInfo);
		
		if(testDatas.length()>0) {
			for(int index=0;index<testDatas.length();++index) {
				testCaseInputs.add(new TestCaseInputs(groupName,deviceToTestOn, 
						testDatas.getJSONObject(index)));
			}
		}else {
			testCaseInputs.add(new TestCaseInputs(groupName,deviceToTestOn));
		}
		
		return testCaseInputs;
	}
	
	private static JSONArray readJSONArray(String groupName) {
		File testDataFile = new File("src/test/resources/testdata/" + groupName + ".json");
		StringBuilder content = new StringBuilder();
		String currentLine;
		
		try(BufferedReader fileReader = new BufferedReader(new FileReader(testDataFile))){
			while((currentLine=fileReader.readLine())!=null) {
				content.append(currentLine);
			}
		} catch (FileNotFoundException e) {
			Logger.log(STATUS.FAIL, e.getLocalizedMessage());
		} catch (IOException e) {
			Logger.log(STATUS.FAIL, e.getLocalizedMessage());
		}
		
		try {
			return new JSONArray(content.toString());
		}catch(Exception e) {
			
			return new JSONArray();
		}
		
		
	}
}
