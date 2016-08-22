package WindTunnel;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.selenium.util.EclipseConnector;

import Utils.MyUtils;
import Utils.PerfectoLabUtils;
import Utils.WindTunnelUtils;

public class StarbucksWebAppWithPersona {

	public static void main(String[] args) throws MalformedURLException, IOException {

		// Define capabilities
		System.out.println("Run started");
		String browserName = "mobileOS";
		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		MyUtils.setCloudCredentials(capabilities);

		// Define Persona
		capabilities.setCapability("windTunnelPersona", WindTunnelUtils.SARA);
		
		// Define Script Name for Reporting
		capabilities.setCapability("scriptName", "WindTunnel-StarbucksWebAppWithPersona");
		runStarbucksTest(capabilities);			

		System.out.println("Run ended");
	}

	private static void runStarbucksTest(DesiredCapabilities capabilities) throws IOException {
		
		// Call this method if you want the script to share the devices with the recording plugin.
		setExecutionIdCapability(capabilities);
        RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + MyUtils.HOST + "/nexperience/perfectomobile/wd/hub"), capabilities);
        
		String app = "Starbucks";
		try {

			// Start logs
			MyUtils.startLogs(driver);
			
			// Go Home
			Map<String, Object> params = new HashMap<>();
			params.put("keySequence", "HOME");
			driver.executeScript("mobile:presskey", params);

			// Close Application
			MyUtils.closeApp(driver, app);
			sleep(3000);
			// Restart Application
			MyUtils.launchApp(driver, app);
			// Start Application & device vitals measurement
			MyUtils.startAppVitals(driver, app, true);

			// Validate the app launched- use OCR to get real UX metrics
			MyUtils.ocrTextCheck(driver, "Stores", -1, 60);

			// Click on stores
			MyUtils.ocrTextClick(driver, "STORES", -1, 60, -1);
			MyUtils.ocrTextCheck(driver, "mi", -1, 60);

			// Click on a store address
			MyUtils.ocrTextClick(driver, "mi", -1, 10, 50);
			String result = MyUtils.ocrTextCheck(driver, "Directions", -1, 60);
			
			if (result.equalsIgnoreCase("true")) {
				// Get directions to store
				MyUtils.ocrTextClick(driver, "Directions", -1, 60, -1);
				MyUtils.ocrTextCheck(driver, "Start", -1, 60);
			}
			else {
				System.out.println("Failed to get directions");
				fail("Expected to get directions");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Script execution failed: " + e.getMessage());
		} finally {

			try {
				// Stop logs and vitals
				MyUtils.stopLogs(driver);
				MyUtils.stopVitals(driver);
			}
			catch (Exception e){
				e.printStackTrace();
			}

			try {
				driver.close();
				String fileName = StarbucksWebAppWithPersona.class.getSimpleName() + "_" + System.currentTimeMillis();
				
				System.out.println("Almost done, downloading report...");
				PerfectoLabUtils.downloadReport(driver, "pdf", MyUtils.REPORT_PATH + "/" + fileName);
				System.out.println("Report file: " + fileName + " Successfuly downloaded!");
				
				String URL = (String)driver.getCapabilities().getCapability("windTunnelReportUrl");
				System.out.println("Report URL: " + URL);				
			} catch (Exception e) {
				e.printStackTrace();
			}
			driver.quit();
		}
	}

 	private static void setExecutionIdCapability(DesiredCapabilities capabilities) throws IOException {
		EclipseConnector connector = new EclipseConnector();
		String executionId = connector.getExecutionId();
		capabilities.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

}
