package com.perfecto.testng.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GenericUtils {

	// TODO: Set your cloud host and credentials
	public static final String HOST = "https://web-demo.perfectomobile.com/nexperience/perfectomobile/wd/hub";
	public static final String USER = "asafs@perfectomobile.com";
	public static final String PASSWORD = "Spike123!";
	public static final String DEVICENAME = "1015FA089D682305";

	// TODO: Set your reports folder path
	public static final String REPORT_PATH = "C:\\Development\\Reports\\";

	public static String getCurrentTime() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	}
}
