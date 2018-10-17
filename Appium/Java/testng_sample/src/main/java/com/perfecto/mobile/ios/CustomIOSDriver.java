package com.perfecto.mobile.ios;

import java.net.URL;

import org.openqa.selenium.Capabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

@SuppressWarnings("unchecked")
public class CustomIOSDriver extends IOSDriver<MobileElement>{

	public CustomIOSDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress, desiredCapabilities);
	}

}
