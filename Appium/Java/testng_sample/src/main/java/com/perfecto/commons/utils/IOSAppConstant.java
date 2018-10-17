package com.perfecto.commons.utils;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.openqa.selenium.remote.DesiredCapabilities;

public class IOSAppConstant {

	private static IOSAppConstant iOSAppConst;
	private Properties appConst;

	private IOSAppConstant() {
		SuiteConstants suiteConst = new SuiteConstants();
		suiteConst.loadPropertiesFile("src/test/resources/ios/app.properties");
		appConst = suiteConst.getProperties();

	}
	
	public static IOSAppConstant getAppConstant() {
		if(iOSAppConst==null) {
			iOSAppConst = new IOSAppConstant();
		}
		
		return iOSAppConst;
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
