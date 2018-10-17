package com.perfecto.commons.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.perfecto.commons.testng.Logger;
import com.perfecto.commons.testng.Logger.STATUS;

public class SuiteConstants {
	
	private Properties properties;
	
	public void loadPropertiesFile(String filePath) {
		File file = new File(filePath);
		try(FileReader fileReader = new FileReader(file)){
			this.properties = new Properties();
			this.properties.load(fileReader);
		} catch (FileNotFoundException e) {
			Logger.log(STATUS.FAIL, e.getMessage());
		} catch (IOException e) {
			Logger.log(STATUS.FAIL, e.getMessage());
		}
	}
	
	public Properties getProperties() {
		return properties;
	}
}
