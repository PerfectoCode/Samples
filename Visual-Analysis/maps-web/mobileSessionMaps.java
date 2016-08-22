import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class mobileSessionMaps{
	
	AndroidDriver driver;
	
	public mobileSessionMaps() throws MalformedURLException{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("user", WebMapsTest.user);
		capabilities.setCapability("password", WebMapsTest.pswd); 
		capabilities.setCapability("deviceName", "MyDeviceID");
		
		driver = new AndroidDriver<>(new URL("https://" + WebMapsTest.host + "/nexperience/perfectomobile/wd/hub"), capabilities);
	}
	
	public void AcceptMapsDirections(){
		try{
			driver.openNotifications();
			driver.context("NATIVE_APP");
			driver.findElement(By.xpath("//*[@resource-id=\"com.android.systemui:id/notification_stack_scroller\"]/android.widget.FrameLayout[1]")).click();
			
			System.out.println("Successfully recived directions to mobile");
			
			Thread.sleep(5000); //waits 5 seconds befor end of the mobile session
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			try {
				// Retrieve the URL of the Wind Tunnel Report, can be saved to
				// your execution summary and used to download the report at a later point
				String reportURL = (String)(driver.getCapabilities().getCapability(WindTunnelUtils.WIND_TUNNEL_REPORT_URL_CAPABILITY));
				
				driver.close();
				
				Map<String, Object> params = new HashMap<>();
				driver.executeScript("mobile:execution:close", params);

				// In case you want to download the report or the reportattachments, do it here.
				PerfectoLabUtils.downloadReport(driver, "pdf", "google maps - Mobile_report");
				// PerfectoLabUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
				// PerfectoLabUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			driver.quit();
		}
	}
	
}