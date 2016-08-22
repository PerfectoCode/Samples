package Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Pages.CreateAccountPage;
import Pages.EmailEntry;
import Pages.OpenPage;
import Pages.UserDetailsPage;
import Pages.WeightGoalPage;
import Utils.PerfectoLabUtils;
import Utils.WindTunnelUtils;

public class NoomTest {
	private AppiumDriver<MobileElement> driver;
	private String opSystem;
	private String reportName;
	
	@DataProvider(name = "WeightLoss")
	public static Object[][] targets() { 
		return new Object[][] {
			{ new wlProfile("Joey", "Male", 33, 176, 85, 10) }, 
			{ new wlProfile("Lisa", "Female", 42, 170, 70, 5) }
		};
	}
	
	@Test(dataProvider = "WeightLoss")
	public void testRegister(wlProfile profile) {
		UserDetailsPage details;
		WeightGoalPage goals;
		
		reportName = "_" + profile.getFname() + "_" + opSystem;

		// Branch #1 - Only iOS implementation may display the popup
		if (opSystem.equalsIgnoreCase("ios"))
			PopUps.checkNotif(driver);
		OpenPage noom = new OpenPage(driver, opSystem);
		CreateAccountPage acct = noom.getStarted();
		//Branch #2 - Android goes straight to Profile details
		if (opSystem.equalsIgnoreCase("android")) {
			details = (UserDetailsPage) acct.startAcct();			
		} else {
			// iOS version first takes the weight-loss goal
			goals = (WeightGoalPage) acct.startAcct();
			//goals.pickUnits("lb");
			//goals.selectGoal(profile.getGoal());
			details = goals.setGoal();
		}
		
		details.setGender(profile.getGender());
		details.enterHt(profile.getHeight(), "cm");
		details.setAge(profile.getAge());
		// Branch #3 - Only Android requires a "first name" but no initial weight
		if (opSystem.equalsIgnoreCase("android")) {
			details.enterName(profile.getFname());
		} else {
			// iOS version requires initial weight but no "first name"
			details.enterWt(profile.getWeight(), "kg");
		}
		
		EmailEntry email = details.gotoEmail();	
	}

	@Parameters({ "OperSys", "OSver", "pkgName", "repAppLoc", "cloud", "userN", "userPw" })
	@BeforeMethod
	public void setupDriver(String opS, String vers, String appID, String repKey, 
			String cloud, String user, String pw) {
		opSystem = opS;
        DesiredCapabilities capabilities = new DesiredCapabilities("", "", Platform.ANY);
        String host = cloud;
        capabilities.setCapability("user", user);
        capabilities.setCapability("password", pw);
        
        // provide application parameters
        capabilities.setCapability("app", repKey);
        capabilities.setCapability("fullReset", true);
        if (opS.equalsIgnoreCase("android"))
        	capabilities.setCapability("appPackage", appID);
        else
        	capabilities.setCapability("bundleId", appID);
        
        // provide device parameters
        capabilities.setCapability("platformName", opS);
        capabilities.setCapability("platformVersion", vers);
        if (opS.equalsIgnoreCase("ios")) {
        	capabilities.setCapability("model", "iPhone-6.*");
        }
        
        // Use the automationName capability to define the required framework - Appium (this is the default) or PerfectoMobile.
        capabilities.setCapability("automationName", "Appium");

        // Call this method if you want the script to share the devices with the Perfecto Lab plugin.
        try {
        	// PerfectoLabUtils.setExecutionIdCapability(capabilities, host);
        	if (opS.equalsIgnoreCase("android")) {
        		driver = (AppiumDriver<MobileElement>) new AndroidDriver<MobileElement>(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        	} else {
        		driver = (AppiumDriver<MobileElement>) new IOSDriver<MobileElement>(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        		PopUps.checkLogin(driver);
        	}
        } catch (Exception e) {
        	// ignore the exception
        }     
        
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        
	}
	
	@AfterMethod
	public void finishUp() {
        try {
            // Retrieve the URL of the Single Test Report, can be saved to your execution summary and used to download the report at a later point
            String reportURL = (String)(driver.getCapabilities().getCapability(WindTunnelUtils.SINGLE_TEST_REPORT_URL_CAPABILITY));

            driver.close();

            // In case you want to download the report or the report attachments, do it here.
            PerfectoLabUtils.downloadReport(driver, "pdf", "C:\\test\\report" + reportName);
            // PerfectoLabUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
            // PerfectoLabUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
        System.out.println("Run ended");
	}
}
