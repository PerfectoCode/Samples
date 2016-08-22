import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class Reddit {

    AppiumDriver driver;

    @Parameters({"platformName", "platformVersion","model" , "browserName", "browserVersion" , "deviceName"})
    @BeforeMethod
    public void beforeTest(String platformName, String platformVersion,String model , String browserName, String browserVersion, String deviceName) throws MalformedURLException {
        System.out.println("Run Started");

        driver = Utils.InitAppiumDriver(platformName, platformVersion,model, browserName, browserVersion, deviceName);
        driver.manage().timeouts().implicitlyWait(20 , TimeUnit.SECONDS);
    }

    @Test
    public void test(){
        System.out.println("Getting app main page");
        mainPage main = new mainPage(driver);

        //Choosing one of the main page buttons
        System.out.println("Choosing login button");
        main.Choose(mainPageBTNS.LogIN);

        //TODO: add your Reddit user and password
        System.out.println("Login app");
        LogIn login = new LogIn(driver, Utils.AppUser, Utils.AppPass); //Taking care of the login page

        //Navigate to search and searching for a given text
        System.out.println("Navigate to search view and searching text");
        postLoginPage postLogin = new postLoginPage(driver);
        //TODO: add search value
        postLogin.search("Israel"); //Text to search

        System.out.println("Test Ended");
    }

    @AfterMethod
    public void tearDown(){
        System.out.println("Run Ended");

        driver.closeApp();
        driver.resetApp();
        driver.quit();
    }

}
