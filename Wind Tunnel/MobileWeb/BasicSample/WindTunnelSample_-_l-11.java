import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.*;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.Cookie.Builder;


import com.perfectomobile.selenium.util.EclipseConnector;


public class RemoteWebDriverTest {


    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println("Run started");


        String browserName = "mobileOS";
        DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
        String host = "myHost.perfectomobile.com";
        capabilities.setCapability("user", "myUser");
        capabilities.setCapability("password", "myPassword");


        //We don't want to select the device, and instead use person device
        //capabilities.setCapability("deviceName", "12345");


        // Use the automationName capability to define the required framework - Appium (this is the default) or PerfectoMobile.
        // capabilities.setCapability("automationName", "PerfectoMobile");


        // Call this method if you want the script to share the devices with the Perfecto Lab plugin.
        setExecutionIdCapability(capabilities, host);


        // Add a persona to your script (see https://community.perfectomobile.com/posts/1048047-available-personas)
        capabilities.setCapability(WindTunnelUtils.WIND_TUNNEL_PERSONA_CAPABILITY, WindTunnelUtils.GEORGIA);


        // Name your script
        capabilities.setCapability("scriptName", "WindTunnelSample");


        RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);


        try {
            // write your code here
            Map<String, Object> params = new HashMap<>();​
​
​            //Start collecting device vitals
​            params.clear();
            Object result =                 driver.executeScript("mobile:monitor:start", params);​            ​
            // Browse to the website on the default browser
            driver.get("http://nxc.co.il/demoaut/index.php");
                        
            // Verify that arrived at the correct page, look for the Header Text
            params.clear();
            params.put("content", "Perfecto Virtual");
            params.put("timeout", "20");


            String resultString = (String) driver.executeScript("mobile:checkpoint:text", params);
            
            if (!resultString.equalsIgnoreCase("true")) {
            // if the browser is not displaying the correct page notify and stop the script
            System.out.println("'Not displaying the opening web page");
            // Wind Tunnel: Add failure point of interest to the Wind Tunnel report
            WindTunnelUtils.pointOfInterest(driver, "Point Of Interest 1: Page load", WindTunnelUtils.FAILURE);
            throw new NoSuchFieldException();
            }
            
            // Wind Tunnel: Add success point of interest to the Wind Tunnel report
            WindTunnelUtils.pointOfInterest(driver, "Point Of Interest 1: Page load", WindTunnelUtils.SUCCESS);
                            
            // search for the username field
            WebElement nameField = driver.findElement(By.name("username"));
            nameField.sendKeys("John");
            
            // search for password field and enter the pw
            WebElement pwField = driver.findElement(By.name("password"));
            pwField.sendKeys("Perfecto1");
    
            // find the Sign in button and click on it
            WebElement signInBtn = driver.findElementByLinkText("Sign in");
            signInBtn.click();
            
            // verify that the correct page is displayed as result of signing in.
            Map<String, Object> params1 = new HashMap<>();
            // Check for the text that indicates that the sign in was successful
            params1.put("content", "Welcome back John");
            
            // allow up-to 30 seconds for the page to display
            params1.put("timeout", "30");
            // Wind Tunnel: Adding accurate timers to text checkpoint command
            params1.put("measurement", "accurate");
            params1.put("source", "camera");
            params1.put("analysis", "automatic");
            params1.put("threshold", "90");
            resultString = (String) driver.executeScript("mobile:checkpoint:text", params1);
            
            // Wind Tunnel: Measure UX timer 1
            long uxTimer1 = timerGet(driver, "ux");
            System.out.println("'Measured UX time is: " + uxTimer1);
                        
            // Wind Tunnel: Add timer to Wind Tunnel Report
            WindTunnelUtils.reportTimer(driver, uxTimer1, 2000, "Checkpoint load time at welcome page.", "uxTimer1");


            
            if (!resultString.equalsIgnoreCase("true")) {
                System.out.println("'Welcome back John' text not found");
                // Wind Tunnel: Add failure point of interest to the Wind Tunnel report
                WindTunnelUtils.pointOfInterest(driver, "Point Of Interest 2: Welcome page", WindTunnelUtils.FAILURE);
            }
            
            // Wind Tunnel: Add success point of interest to the Wind Tunnel report
            WindTunnelUtils.pointOfInterest(driver, "Point Of Interest 2: Welcome page", WindTunnelUtils.SUCCESS);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
​                // Stop collection of device vitals
​                params.clear();
​                result = driver.executeScript("mobile:monitor:stop", params);

                driver.close();


                // In case you want to download the report or the report attachments, do it here.
                // PerfectoLabUtils.downloadReport(driver, "pdf", "C:\\test\\report");
                // PerfectoLabUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
                // PerfectoLabUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");


            } catch (Exception e) {
                e.printStackTrace();
            }


            driver.quit();
        }


        System.out.println("Run ended");
    }


    // Wind Tunnel: Gets the user experience (UX) timer
    private static long timerGet(RemoteWebDriver driver, String timerType) {
         String command = "mobile:timer:info";
         Map<String,String> params = new HashMap<String,String>();
         params.put("type", timerType);
         long result = (long)driver.executeScript(command, params);
             return result;
    }
    
    private static void switchToContext(RemoteWebDriver driver, String context) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", context);
        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
    }


    private static String getCurrentContextHandle(RemoteWebDriver driver) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        String context =  (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
        return context;
    }


    private static List<String> getContextHandles(RemoteWebDriver driver) {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        List<String> contexts =  (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
        return contexts;
    }


    private static void setExecutionIdCapability(DesiredCapabilities capabilities, String host) throws IOException {
        EclipseConnector connector = new EclipseConnector();
        String eclipseHost = connector.getHost();
        if ((eclipseHost == null) || (eclipseHost.equalsIgnoreCase(host))) {
            String executionId = connector.getExecutionId();
            capabilities.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);
        }
    }
}