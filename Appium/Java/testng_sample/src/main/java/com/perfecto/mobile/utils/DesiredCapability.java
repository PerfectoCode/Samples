package com.perfecto.mobile.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.perfecto.commons.testng.Logger;
import com.perfecto.commons.testng.Logger.STATUS;
import com.perfecto.commons.utils.AndroidAppConstant;
import com.perfecto.commons.utils.PerfectoCloudInformation;
import com.perfecto.commons.utils.DevicesToTestOn.Device;
import com.perfecto.commons.utils.IOSAppConstant;

public class DesiredCapability {

	public static DesiredCapabilities getDesiredCapabilities(Device deviceInformation) {

		PerfectoCloudInformation perfectoCloudInformation = PerfectoCloudInformation.loadPerfectoConstants();
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		
		if(!"".equals(perfectoCloudInformation.getConstant("perfecto.username"))) {
			desiredCapabilities.setCapability("user", perfectoCloudInformation.getConstant("perfecto.username"));
		}
		
		if(!"".equals(perfectoCloudInformation.getConstant("perfecto.password"))) {
			desiredCapabilities.setCapability("password", perfectoCloudInformation.getConstant("perfecto.password"));
		}
		
		if(!"".equals(perfectoCloudInformation.getConstant("perfecto.apikey"))) {
			desiredCapabilities.setCapability("securityToken", perfectoCloudInformation.getConstant("perfecto.apikey"));
		}
		

		Map<String, String> capabilities = deviceInformation.getDeviceAttributes();

		Set<Entry<String, String>> entries = capabilities.entrySet();

		Iterator<Entry<String, String>> entryIterator = entries.iterator();

		Entry<String, String> currentEntry;

		while (entryIterator.hasNext()) {
			currentEntry = entryIterator.next();
			desiredCapabilities.setCapability(currentEntry.getKey(), currentEntry.getValue());
		}

		if (capabilities.containsKey("platformName")) {
			switch (capabilities.get("platformName").toUpperCase()) {
			case "ANDROID":
				AndroidAppConstant androidAppConst = AndroidAppConstant.getAppConstant();
				androidAppConst.setAppConstants(desiredCapabilities);
				break;
			case "IOS":
				IOSAppConstant iOSAppConst = IOSAppConstant.getAppConstant();
				iOSAppConst.setAppConstants(desiredCapabilities);
				break;
			}

		} else {
			Logger.log(STATUS.FATAL,
					"Platform Name is Mandatory. Please provide platformName parameter in device_selection.properties");

		}

		return desiredCapabilities;
	}

}
