package com.perfecto.testng.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenericUtils {

	// TODO: Set your cloud host and credentials
	public static final String HOST = "https://MY_HOST.perfectomobile.com/nexperience/perfectomobile/wd/hub";
	public static final String USER = "MY_USER";
	public static final String PASSWORD = "MY_PASSWORD";
	public static final String DEVICENAME = "MY_DEVICE_ID";

	// TODO: Set your reports folder path
	public static final String REPORT_PATH = "C:/Development/Reports/";

	public static String getCurrentTime() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	}
}
