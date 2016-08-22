import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by daniela on 8/3/2016.
 */
public class Utils {

    //TODO: Set your Perfecto lab user, pass and host name
    static String Perfecto_User = System.getProperty("np.testUser", "MY_USER");
    static String Perfecto_Pass = System.getProperty("np.testPass", "MY_PASS");
    static String Perfecto_Host = System.getProperty("np.testHost", "MY_HOST.perfectomobile.com");

    static String Application_Path = System.getProperty("np.testApp", "PRIVATE:Reddit The Official App_v1.5.3_apkpure.com.apk");
    static String AppUser = System.getProperty("np.AppUser", "My_user");
    static String AppPass = System.getProperty("np.AppPass", "My_pass");

    /**
     * Receives device capabilities from the test main class and initialize Appium driver.
     * @param platformName
     * @param platformVersion
     * @param browserName
     * @param browserVersion
     * @return iOSDriver if platformName equals "iOS" otherwise returns AndroidDriver
     */
    public static AppiumDriver InitAppiumDriver(String platformName, String platformVersion,String model , String browserName, String browserVersion, String deviceName)
            throws MalformedURLException {
        //User capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("user" , Perfecto_User);
        capabilities.setCapability("password" , Perfecto_Pass);

        //Device Capabilities
        capabilities.setCapability("platformName" , platformName);
        capabilities.setCapability("platformVersion" , platformVersion);
        capabilities.setCapability("model" , model);
        capabilities.setCapability("browserName" , browserName);
        capabilities.setCapability("browserVersion" , browserVersion);
        capabilities.setCapability("deviceName" , deviceName);

        //App Capabilities
        capabilities.setCapability("app", Application_Path);
        capabilities.setCapability("fullReset", true);
        capabilities.setCapability("appPackage" , "com.reddit.frontpage");
        if(platformName.equals("iOS"))
            return new IOSDriver(new URL("https://" + Perfecto_Host + "/nexperience/perfectomobile/wd/hub") , capabilities);

        else
            return new AndroidDriver(new URL("https://" + Perfecto_Host + "/nexperience/perfectomobile/wd/hub") , capabilities);
    }

}
