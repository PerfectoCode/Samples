import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;
import java.net.MalformedURLException;
import com.perfectomobile.selenium.util.EclipseConnector;

public class WebMapsTest {

	// TODO: Set your Perfecto Lab host,user and password
	static String host = "MY_HOST.perfectomobile.com";
	static String user = "MY_USER";
	static String pswd = "MY_PASS";

	//TODO: set your Google account
	static String Google_user = "MY_USER@google.com";
	static String Google_Pass = "MY_PASS";

	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println("Run started");
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("user", user);
		capabilities.setCapability("password", pswd);

		// TODO: Change browser capabilities
		capabilities.setCapability("platformName", "Windows");
		capabilities.setCapability("platformVersion", "7");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("browserVersion", "48");
		capabilities.setCapability("resolution", "1366x768");
		capabilities.setCapability("location", "US East");

		setExecutionIdCapability(capabilities, host);
		capabilities.setCapability("scriptName", "WebMapsTest");
		
		//Creates RemoteWebDriver
		RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub") , capabilities);
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		Map<String, Object> params = new HashMap<>();
		
		try {
			// Open site and verify main page display via direction icon + click (select)
			driver.get("https://www.google.com/maps");
			params.put("content", "PRIVATE:maps/maps - directions icon.png");
			params.put("timeout", "30");
			Object result = driver.executeScript("mobile:image:select", params);

			// Verify directions pane display
			params.clear();
			params.put("content", "PRIVATE:maps/maps - directions page indicator.png");
			params.put("timeout", "30");
			result = driver.executeScript("mobile:image:find", params);

			String source = "5 India St, Boston, MA 02109, USA";
			String destination = "5 Austin St, Charlestown, MA 0212";
			// Use objects to set origin and destination
			driver.findElement(By.xpath("//*[@id='sb_ifc51']/input")).sendKeys(source);
			driver.findElement(By.xpath("//*[@id='sb_ifc52']/input")).sendKeys(destination);

			// Click on find driving directions icon
			params.clear();
			params.put("content", "PRIVATE:maps/maps - driving directions icon.png");
			params.put("timeout", "10");
			result = driver.executeScript("mobile:image:select", params);

			// Verify pane contains suggested routes from source to destination
			params.clear();
			params.put("content", "Send directions to your phone");
			params.put("timeout", "30");
			result = driver.executeScript("mobile:text:find", params);

			// Select first route
			WebElement firstRoute = driver
					.findElement(By.xpath("//div[@class='widget-pane-section-listbox' and @role='listbox']/div[1]"));
			String navigationTime = firstRoute
					.findElement(By
					.xpath("//div[@class='widget-pane-section-directions-trip-duration delay-light'][1]/span[1]"))
					.getText();
			
			//Printing to the console the route info
			System.out.println("Driving from " + source + " to " + destination + " takes " + navigationTime + ".");
			String distance = firstRoute.findElement(By.xpath("//div[ancestor::div[contains(@class,'trip-distance')]]"))
					.getText();
			System.out.println("This drive is " + distance + "long.");
			String routeTitle = "via "
					+ firstRoute.findElement(By.xpath("//h1[contains(@class,'trip-title')]/span")).getText();
			System.out.println("The route goes " + routeTitle);

			// Click directions to the mobile (Before login)
			Map<String, Object> SendDirectionsToMobile = new HashMap<>();
			SendDirectionsToMobile.put("label", "Send directions to your phone");
			result = driver.executeScript("mobile:button-text:click", SendDirectionsToMobile);

			// Press SIGN IN button
			Map<String, Object> params2 = new HashMap<>();
			params2.put("label", "SIGN IN");
			result = driver.executeScript("mobile:button-text:click", params2);

			// Inser email+pass
			driver.findElement(By.name("Email")).sendKeys(Google_user);
			driver.findElement(By.id("next")).click();
			driver.findElement(By.name("Passwd")).sendKeys(Google_Pass);
			driver.findElement(By.id("signIn")).click();

			// Send directions to the mobile device (After login)
			driver.executeScript("mobile:button-text:click", SendDirectionsToMobile);
			driver.findElement(By.xpath("//*[@class = 'send-to-device-list-item'][2]/button")).click(); //Choosing device

			// Starts the mobile session from here
			new mobileSessionMaps().AcceptMapsDirections(); 

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Retrieve the URL of the Wind Tunnel Report, can be saved to
				// your execution summary and used to download the report at a later point
				String reportURL = (String)(driver.getCapabilities().getCapability(WindTunnelUtils.WIND_TUNNEL_REPORT_URL_CAPABILITY));

				driver.close();
				params.clear();
				driver.executeScript("mobile:execution:close", params);

				// In case you want to download the report or the reportattachments, do it here.
				PerfectoLabUtils.downloadReport(driver, "pdf", "google maps - web_report");
				// PerfectoLabUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
				// PerfectoLabUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

			} catch (Exception e) {
				e.printStackTrace();
			}

			driver.quit();
		}

		System.out.println("Run ended");
	}

	
	public static void setExecutionIdCapability(DesiredCapabilities capabilities, String host) throws IOException {
		EclipseConnector connector = new EclipseConnector();
		String eclipseHost = connector.getHost();
		if ((eclipseHost == null) || (eclipseHost.equalsIgnoreCase(host))) {
			String executionId = connector.getExecutionId();
			capabilities.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);
		}
	}

}
