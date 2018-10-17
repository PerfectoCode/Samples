package com.perfecto.commons.utils;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.remote.DesiredCapabilities;

public class AndroidAppConstant {
	
	private static AndroidAppConstant androidAppConst;
	private Properties appConst;

	private AndroidAppConstant() {
		SuiteConstants suiteConst = new SuiteConstants();
		suiteConst.loadPropertiesFile("src/test/resources/android/app.properties");
		appConst = suiteConst.getProperties();

	}
	
	public static AndroidAppConstant getAppConstant() {
		if(androidAppConst==null) {
			androidAppConst = new AndroidAppConstant();
		}
		
		return androidAppConst;
	}
	
	public String getConstant(String constantName) {
		return this.appConst.getProperty(constantName, "");
	}
	
	
	public void setAppConstants(DesiredCapabilities capability) {
		Set<Entry<Object,Object>> entries = this.appConst.entrySet();
		Iterator<Entry<Object,Object>> entryIterator = entries.iterator();
		Entry<Object,Object> currentEntry;
		while(entryIterator.hasNext()) {
			currentEntry = entryIterator.next();
			capability.setCapability((String)currentEntry.getKey(), (String)currentEntry.getValue());
		}
	}
}
