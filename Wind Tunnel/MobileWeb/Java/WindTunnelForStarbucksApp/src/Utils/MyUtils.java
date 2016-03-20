package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class MyUtils {

	// TODO: Set your cloud host and credentials
	public static final String HOST = "MY_HOST.perfectomobile.com";
	private static final String USERNAME = "MY_USER";
	private static final String PASSWORD = "MY_PASSWORD";
	private static final String DEVICENAME = "MY_DEVICE_NAME";

	// TODO: Set your reports folder path
	public static final String REPORT_PATH = "C:\\Users\\MY_USER\\Automation\\Reports";

	
	public static void setCloudCredentials(DesiredCapabilities capabilities) {
		capabilities.setCapability("user", USERNAME);
		capabilities.setCapability("password", PASSWORD);
		capabilities.setCapability("deviceName", DEVICENAME);
	}

	// Wind Tunnel: Gets the requested timer
	public static long timerGet(RemoteWebDriver driver, String timerType) {
		String command = "mobile:timer:info";
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", timerType);
		long result = (long) driver.executeScript(command, params);
		return result;
	}

	// returns UX timer
	public static long getUXTimer(RemoteWebDriver driver) {
		long timer = timerGet(driver, "ux");
		return timer;
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	// Switched driver context
	public static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}

	// Gets current context
	public static String getCurrentContextHandle(RemoteWebDriver driver) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		String context = (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
		return context;
	}

	// Get available context
	@SuppressWarnings("unchecked")
	public static List<String> getContextHandles(RemoteWebDriver driver) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		List<String> contexts = (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
		return contexts;
	}

	// Perform text check OCR function
	public static String ocrTextCheck(RemoteWebDriver driver, String text, int threshold, int timeout) {
		// Verify that arrived at the correct page, look for the Header Text
		Map<String, Object> params = new HashMap<>();
		params.put("content", text);
		params.put("timeout", Integer.toString(timeout));
		params.put("measurement", "accurate");
		params.put("source", "camera");
		params.put("analysis", "automatic");
		if (threshold > 0) {
			params.put("threshold", Integer.toString(threshold));
		}
		String result = (String) driver.executeScript("mobile:checkpoint:text", params);
		System.out.println("OCR text check result = " + result + ", params = " + params);
		return result;
	}

	// Performs text click OCR function
	public static void ocrTextClick(RemoteWebDriver driver, String text, int threshold, int timeout, int top) {
		Map<String, Object> params = new HashMap<>();
		params.put("content", text);
		params.put("timeout", Integer.toString(timeout));
		if (threshold > 0) {
			params.put("threshold", Integer.toString(threshold));
		}
		if (top > 0) {
			params.put("screen.top", Integer.toString(top) + "%");
			params.put("screen.height", Integer.toString(100 - top) + "%");
		}
		driver.executeScript("mobile:text:select", params);
	}

	// Performs image click OCR function
	public static String ocrImageSelect(RemoteWebDriver driver, String img) {
		Map<String, Object> params = new HashMap<>();
		params.put("content", img);
		params.put("screen.top", "0%");
		params.put("screen.height", "100%");
		params.put("screen.left", "0%");
		params.put("screen.width", "100%");
		return (String) driver.executeScript("mobile:image:select", params);
	}

	// Performs image click OCR function
	public static String ocrImageCheck(RemoteWebDriver driver, String img, int timeout) {
		Map<String, Object> params = new HashMap<>();
		params.put("content", img);
		params.put("screen.top", "0%");
		params.put("screen.height", "100%");
		params.put("screen.left", "0%");
		params.put("screen.width", "100%");
		params.put("timeout", Integer.toString(timeout));
		return (String) driver.executeScript("mobile:checkpoint:image", params);
	}

	// Launches application
	public static String launchApp(RemoteWebDriver driver, String app) {
		Map<String, Object> params = new HashMap<>();
		params.put("name", app);
		return (String) driver.executeScript("mobile:application:open", params);
	}

	// Closes application
	public static String closeApp(RemoteWebDriver driver, String app) {
		String result = "false";
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("name", app);
			result = (String) driver.executeScript("mobile:application:close", params);
		} catch (Exception ex) {
		}
		return result;
	}

	// Add a comment
	public static String comment(RemoteWebDriver driver, String comment) {
		Map<String, Object> params = new HashMap<>();
		params.put("text", comment);
		return (String) driver.executeScript("mobile:comment", params);
	}

	// checks if element exists
	public static Boolean elementExists(RemoteWebDriver driver, String xPath) {
		try {
			driver.findElementByXPath(xPath);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static String startAppVitals(RemoteWebDriver driver, String app, boolean startDeviceVitals) {
		Map<String, Object> params = new HashMap<>();
		List<String> vitals = new ArrayList<>();
		vitals.add("all");
		params.put("vitals", vitals);
		params.put("interval", Long.toString(1));
		List<String> sources = new ArrayList<>();
		sources.add(app);
		if (startDeviceVitals)
			sources.add("device");
		params.put("sources", sources);
		return (String) driver.executeScript("mobile:monitor:start", params);
	}

	public static String stopVitals(RemoteWebDriver driver) {
		Map<String, Object> params = new HashMap<>();
		List<String> vitals = new ArrayList<>();
		vitals.add("all");
		params.put("vitals", vitals);
		return (String) driver.executeScript("mobile:monitor:stop", params);
	}

	public static String startLogs(RemoteWebDriver driver) {
		Map<String, Object> params = new HashMap<>();
		return (String) driver.executeScript("mobile:logs:start", params);
	}

	public static String stopLogs(RemoteWebDriver driver) {
		Map<String, Object> params = new HashMap<>();
		return (String) driver.executeScript("mobile:logs:stop", params);
	}

}