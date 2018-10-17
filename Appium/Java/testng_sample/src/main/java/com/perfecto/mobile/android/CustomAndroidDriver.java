package com.perfecto.mobile.android;

import java.net.URL;

import org.openqa.selenium.Capabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

@SuppressWarnings("unchecked")
public class CustomAndroidDriver extends AndroidDriver<MobileElement>{

	public CustomAndroidDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress, desiredCapabilities);
	}
}
