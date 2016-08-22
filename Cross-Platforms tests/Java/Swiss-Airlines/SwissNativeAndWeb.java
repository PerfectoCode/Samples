import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.NoSuchElementException;

import org.openqa.selenium.*;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.Cookie.Builder;

import io.appium.java_client.*;
import io.appium.java_client.android.*;
import io.appium.java_client.ios.*;

import com.perfectomobile.selenium.util.EclipseConnector;

public class SwissNativeAndWeb {

    static SwissPageObjects mobileObj;
    static SwissPageObjects desktopObj;

    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println("Run started");

        String browserName = "mobileOS";
        Map<String, String> mobileFlightData = new HashMap<>();
        Map<String, String> desktopFlightData = new HashMap<>();
        
        String host = "yourlab.perfectomobile.com";
        String user = "yourUserName"; 
        String pswd = "yourPassword";

        //mobile capabilities
        DesiredCapabilities mobileCapabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
        mobileCapabilities.setCapability("user", user);
        mobileCapabilities.setCapability("password", pswd);
        //Device ID where Swiss native app is installed
        mobileCapabilities.setCapability("deviceName", "DeviceWithInstrumentedAppInstalled");
        mobileCapabilities.setCapability("automationName", "Appium");
        setExecutionIdCapability(mobileCapabilities, host);
        mobileCapabilities.setCapability("scriptName", "SwissNativeAndWeb");
        mobileCapabilities.setCapability("WindTunnelPersona", "Georgia");
        mobileCapabilities.setCapability("fullRest", true);
        mobileCapabilities.setCapability("appPackage", "com.yoc.swiss.swiss");
        
        //Desktop capabilities
        DesiredCapabilities desktopCapabilities = new DesiredCapabilities();
        desktopCapabilities.setCapability("user", user);
        desktopCapabilities.setCapability("password", pswd);
        desktopCapabilities.setCapability("platformName", "Windows");
        desktopCapabilities.setCapability("platformVersion", "8.1");
        desktopCapabilities.setCapability("browserName", "Firefox");
        desktopCapabilities.setCapability("browserVersion", "46");
        desktopCapabilities.setCapability("resolution", "1366x768");
        desktopCapabilities.setCapability("location", "US East");

        //Creating drivers
        AndroidDriver mobileDriver = new AndroidDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), mobileCapabilities);
        mobileDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        

        RemoteWebDriver desktopDriver = new RemoteWebDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), desktopCapabilities);
        desktopDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        desktopDriver.manage().window().maximize();
        desktopDriver.get("https://www.swiss.com/us/en");
        
        //Creating objects containing all relevant object definitions
        mobileObj = new SwissPageObjects("Mobile");
        desktopObj = new SwissPageObjects("Desktop");
    	Map<String, Object> params = new HashMap<>();

        try {
        	
        	//Define search data: from Zurich to London, 20-30/6
        	WebDriverWait desktopWait = new WebDriverWait(desktopDriver, 15);
        	try {
        		desktopWait.until(ExpectedConditions.visibilityOf(desktopDriver.findElement(desktopObj.flightTabId)));
    		} catch (NoSuchElementException e) {
    			System.out.println("Could not find Swiss opening page on desktop");
    		}
        	
        	desktopDriver.findElement(desktopObj.flightTabId).click();
        	desktopDriver.findElement(desktopObj.from).clear();
        	desktopDriver.findElement(desktopObj.from).sendKeys("Zurich (ZRH)");
        	desktopDriver.findElement(desktopObj.selectFrom).click();
        	desktopDriver.findElement(desktopObj.to).clear();
        	desktopDriver.findElement(desktopObj.to).sendKeys("London (LON)");
        	desktopDriver.findElement(desktopObj.selectTo).click();        	
        	desktopDriver.findElement(desktopObj.fromDate).click();
        	desktopDriver.findElement(desktopObj.fromDateSet).click();
        	desktopDriver.findElement(desktopObj.toDateSet).click();
        	desktopDriver.findElement(desktopObj.searchFlightButton).click();

        	//now in mobile
        	mobileDriver.context("NATIVE_APP");
        	params.put("content", "Book a flight");
        	params.put("timeout", "10");
        	params.put("match", "contain");
        	Object result = mobileDriver.executeScript("mobile:text:select", params);
        	       	
        	params.clear();
        	params.put("content", "Book your flight");
        	params.put("timeout", "10");
        	params.put("match", "Contain");
        	result = mobileDriver.executeScript("mobile:text:find", params);        	
 
        	switchToContext(mobileDriver, "WEBVIEW");

        	mobileDriver.findElement(mobileObj.from).sendKeys("Zurich (ZRH)");
        	mobileDriver.findElement(mobileObj.to).sendKeys("London (LON)");
        	mobileDriver.findElement(mobileObj.fromDate).sendKeys("20/06/2016");      	
        	mobileDriver.findElement(mobileObj.toDate).sendKeys("30/06/2016");

        	mobileDriver.findElement(mobileObj.searchFlightButton).click();
        	
        	//Select flights to and from
        	try {
        		desktopWait.until(ExpectedConditions.visibilityOf(desktopDriver.findElement(desktopObj.flightToSelectPage)));
    		} catch (NoSuchElementException e) {
    			System.out.println("Could not find flights to match the requirements");
    		}
        	WebElement flightTree = desktopDriver.findElement(desktopObj.firstFlightTree);
        	desktopFlightData = GetFlightDataDesktop(flightTree);
        	System.out.println("------------------------------");
        	System.out.println("Outbound flight");
        	printFlightDetails(desktopFlightData, "desktop");
        	flightTree.findElements(desktopObj.economyPrice).get(0).click();
        	flightTree.findElements(desktopObj.flightSelection).get(0).click();
        
        	try {
        		desktopWait.until(ExpectedConditions.visibilityOf(desktopDriver.findElement(desktopObj.selectReturnFlightBtn)));
    		} catch (NoSuchElementException e) {
    			System.out.println("Could not find button for return flights");
    		}
        	desktopDriver.findElement(desktopObj.selectReturnFlightBtn).click();
        	try {
        		desktopWait.until(ExpectedConditions.visibilityOf(desktopDriver.findElement(desktopObj.flightBackSelectPage)));
    		} catch (NoSuchElementException e) {
    			System.out.println("Could not find flights to match the requirements");
    		}
        	flightTree = desktopDriver.findElement(desktopObj.secondFlightTree);
        	//diff id on the flight back - otherwise the same path
        	desktopObj.economyPrice = By.xpath("//span[parent::button[@data-type='economy'] and contains(@class,'price')]");
        	desktopObj.currency = By.xpath("//span[parent::button[@data-type='economy'] and contains(@class,'currency')]");
        	desktopFlightData = GetFlightDataDesktop(flightTree);
        	System.out.println("------------------------------");
        	System.out.println("Inbound flight");
        	printFlightDetails(desktopFlightData, "desktop");
        	flightTree.findElements(desktopObj.economyPrice).get(0).click();
        	flightTree.findElements(desktopObj.flightSelection).get(0).click();
        	try {
        		desktopWait.until(ExpectedConditions.visibilityOf(desktopDriver.findElement(desktopObj.continueToSeatBtn)));
    		} catch (NoSuchElementException e) {
    			System.out.println("Could not find button for seat selection");
    		}
        	desktopDriver.findElement(desktopObj.continueToSeatBtn).click();

        	//now in mobile       	
        	params.clear();
        	params.put("content", "Select outbound flight");
        	params.put("timeout", "10");
        	params.put("match", "contain");
        	result = mobileDriver.executeScript("mobile:text:find", params);
        	
        	switchToContext(mobileDriver, "WEBVIEW");
        	String flightInfo = ((WebElement) mobileDriver.findElements(mobileObj.firstFlightTree).get(0)).getText();
        	mobileFlightData = GetFlightDataMobile(flightInfo);
        	System.out.println("------------------------------");
        	System.out.println("Outbound flight");
        	printFlightDetails(mobileFlightData, "mobile");
        	mobileDriver.findElement(mobileObj.economyPrice).click();
        	mobileDriver.findElement(mobileObj.flightSelection).click();
        	((WebElement) mobileDriver.findElements(mobileObj.selectReturnFlightBtn).get(0)).click();
        	
        	params.clear();
        	params.put("content", "Select return flight");
        	params.put("timeout", "10");
        	params.put("match", "contain");
        	result = mobileDriver.executeScript("mobile:text:find", params);
        	
        	flightInfo = ((WebElement) mobileDriver.findElements(mobileObj.secondFlightTree).get(0)).getText();
        	mobileFlightData = GetFlightDataMobile(flightInfo);
        	System.out.println("------------------------------");
        	System.out.println("Inbound flight");
        	printFlightDetails(mobileFlightData, "mobile");
        	mobileObj.economyPrice = By.xpath("/html[1]/body[1]/div[3]/div[2]/div[3]/div[1]/span[1]/div[1]/div[1]/div[3]/div[2]/form[1]/div[2]/div[1]/div[2]/div[1]/button[1]/span[1]");
        	mobileDriver.findElement(mobileObj.economyPrice).click();
        	mobileObj.flightSelection = By.xpath("/html[1]/body[1]/div[3]/div[2]/div[3]/div[1]/span[1]/div[1]/div[1]/div[3]/div[2]/form[1]/div[2]/div[1]/div[2]/div[1]/div[1]/ul[1]/li[1]/div[1]/div[2]/label[1]/span[1]/span[1]");
        	mobileDriver.findElement(mobileObj.flightSelection).click();
        	((WebElement) mobileDriver.findElements(mobileObj.continueToSeatBtn).get(0)).click();
        	
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Retrieve the URL of the Wind Tunnel Report, can be saved to your execution summary and used to download the report at a later point
                String reportURL = (String)(mobileDriver.getCapabilities().getCapability(WindTunnelUtils.WIND_TUNNEL_REPORT_URL_CAPABILITY));

                mobileDriver.close();
                desktopDriver.close();
                params.clear();
                desktopDriver.executeScript("mobile:execution:close", params);
                // In case you want to download the report or the report attachments, do it here.
                PerfectoLabUtils.downloadReport(mobileDriver, "pdf", "C:\\test\\report - Swiss airlines");
                // PerfectoLabUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
                // PerfectoLabUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");

            } catch (Exception e) {
                e.printStackTrace();
            }

            mobileDriver.quit();
            desktopDriver.quit();
        }

        System.out.println("Run ended");
    }
    
    private static Map<String, String> GetFlightDataDesktop(WebElement flightTree) {
    	Map<String, String> desktopFlightData = new HashMap<String,String>();
    	
    	WebElement element1 = flightTree.findElement(desktopObj.depInfo);
    	desktopFlightData.put("departureAireport", element1.getText());
    	element1 = flightTree.findElement(desktopObj.depTimeNode);
    	desktopFlightData.put("departureTime", element1.getText());
    	element1 = flightTree.findElement(desktopObj.arvInfo);
    	desktopFlightData.put("arrivalAirport", element1.getText());
    	element1 = flightTree.findElement(desktopObj.arvTimeNode);
    	desktopFlightData.put("arrivalTime", element1.getText());
    	element1 = flightTree.findElements(desktopObj.economyPrice).get(0);
    	WebElement element2 = element1.findElements(desktopObj.price).get(0);
    	desktopFlightData.put("economyPrice", element2.getText());
    	element2 = element1.findElements(desktopObj.currency).get(0);
    	desktopFlightData.put("currency", element2.getText());    	
    	
    	return desktopFlightData;
    }
    
    private static Map<String, String> GetFlightDataMobile(String flightInfo) {
    	Map<String, String> mobileFlightData = new HashMap<String,String>();
    	
    	int openParIndex = flightInfo.indexOf('(');
    	int closeParIndex = flightInfo.indexOf(')');
    	mobileFlightData.put("departureAireport", flightInfo.substring(openParIndex+1, closeParIndex));
    	int colonIndex = flightInfo.indexOf(':');
    	mobileFlightData.put("departureTime", flightInfo.substring(colonIndex-2, colonIndex+3));
    	openParIndex = flightInfo.indexOf('(', openParIndex+1);
    	closeParIndex = flightInfo.indexOf(')', openParIndex);
    	mobileFlightData.put("arrivalAirport", flightInfo.substring(openParIndex+1, closeParIndex));
    	colonIndex = flightInfo.indexOf(':', colonIndex+1);
    	mobileFlightData.put("arrivalTime", flightInfo.substring(colonIndex-2, colonIndex+3));
    	int index = flightInfo.indexOf("from");
    	int spaceIndex = flightInfo.indexOf(' ', index+6);
    	mobileFlightData.put("currency", flightInfo.substring(index+5, spaceIndex));
    	//spaceIndex = flightInfo.indexOf(' ', spaceIndex);
    	index = flightInfo.indexOf('.', spaceIndex);
    	mobileFlightData.put("economyPrice", flightInfo.substring(spaceIndex+1,index));
    	
    	return mobileFlightData;
    }
    
	private static void printFlightDetails(Map<String, String> FlightData, String platform) {
	System.out.println("---------------------------------");
	System.out.println("Results for " + platform + " are:");
	System.out.println("---------------------------------");
	System.out.println("Departure Aireport: " + FlightData.get("departureAireport"));
	System.out.println("Departure Time:     " + FlightData.get("departureTime"));
	System.out.println("Arrival Airport:    " + FlightData.get("arrivalAirport"));
	System.out.println("Arrival Time:       " + FlightData.get("arrivalTime"));
	System.out.println("Economy Price:      " + FlightData.get("economyPrice"));
	System.out.println("currency:           " + FlightData.get("currency"));
	System.out.println("---------------------------------");
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
