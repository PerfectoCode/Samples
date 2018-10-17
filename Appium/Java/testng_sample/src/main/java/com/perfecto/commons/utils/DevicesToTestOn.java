package com.perfecto.commons.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class DevicesToTestOn{

	static private DevicesToTestOn devices;
	private List<Device> devicestoTest;
	
	private DevicesToTestOn() {
		
		SuiteConstants suiteConst = new SuiteConstants();
		suiteConst.loadPropertiesFile("src/test/resources/perfecto/device_selection.properties");
		Properties deviceProperties = suiteConst.getProperties();
		devicestoTest = new ArrayList<>();
		
		Set<Object> keySet = deviceProperties.keySet();
		
		List<Object> sortedKeySet = keySet.stream().sorted().collect(Collectors.toList());
		
		int deviceIndex = 1;
		String currentDevicePrefix;
		Device currentDevice = new Device();
		
		Iterator<Object> keyIterator = sortedKeySet.iterator();
		
		String currentKey;
		
		while(keyIterator.hasNext()) {
			currentKey = (String)keyIterator.next();
			currentDevicePrefix = "DEVICE-" + deviceIndex;
			if(currentKey.startsWith(currentDevicePrefix)) {
			}else {
				devicestoTest.add(currentDevice);
				++deviceIndex;
				currentDevice = new Device();
			}
			currentDevice.addAttribute(currentKey.split("\\.")[1], deviceProperties.getProperty(currentKey));
		}
		
		devicestoTest.add(currentDevice);
		
	}
	
	public List<Device> getDevices(){
		return this.devicestoTest;
	}
	
	public static DevicesToTestOn getDevicesToTestOn() {
		if(devices==null) {
			devices = new DevicesToTestOn();
		}
		
		return devices;
	}
	
	public static class Device{
		
		private Map<String,String> deviceAttributes;
		
		public Device(Map<String,String> deviceAttributes) {
			this.deviceAttributes = deviceAttributes;
		}
		
		public Device() {
			this.deviceAttributes = new HashMap<>();
		}
		
		void addAttribute(String attributeName,String attributeValue) {
			deviceAttributes.put(attributeName, attributeValue);
		}
		
		public Map<String,String> getDeviceAttributes(){
			return this.deviceAttributes;
		}
		
		@Override
		public String toString() {
			
			return deviceAttributes.toString();
		}
		
	}
}
