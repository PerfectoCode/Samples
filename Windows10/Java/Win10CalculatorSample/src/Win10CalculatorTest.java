import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Windows10 test sample using selenium + testNG
 * selenium jars can be found at: http://www.seleniumhq.org/download/
 * testNG jars can be found at: http://testng.org/doc/download.html
 *
 * Yet recommended to use Maven/Gradle in order to manage your dependencies
 */
public class Win10CalculatorTest {

    //Your Perfecto lab user, password and host
    static String PERFECTO_USER = "MY_USER";
    static String PERFECTO_PASS = "MY_PASS";
    static String PERFECTO_HOST = "MY_HOST.perfectomobile.com";

    static RemoteWebDriver driver;

    @BeforeMethod
    @Parameters({"platformName" , "platformVersion" , "manufacturer" , "model"})
    public void beforeTest(String platformName, String platformVersion, String manufacturer, String model) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("user", PERFECTO_USER);
        capabilities.setCapability("password", PERFECTO_PASS);

        System.out.println("Run Started");

        //device capabilities
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("manufacturer", manufacturer);
        capabilities.setCapability("model", model);
        //capabilities.setCapability("deviceName" , "123456"); Optional

        //App settings
        //capabilities.setCapability("app", "PRIVATE:testApp\\App1_1.0.0.0_Debug_Test.zip"); For app which located in repository - not installed yet
        capabilities.setCapability("winAppId", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App"); //app id

        driver = new RemoteWebDriver(new URL("https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub"), capabilities);
    }

    @Test
    public void TestMethod() {
        System.out.println("Test Started");

        driver.findElementByName("Clear").click();
        driver.findElementByName("Two").click();
        driver.findElementByName("Zero").click();
        driver.findElementByName("One").click();
        driver.findElementByName("Six").click();
        driver.findElementByName("Minus").click();
        driver.findElementByName("One").click();
        driver.findElementByName("Nine").click();
        driver.findElementByName("Five").click();
        driver.findElementByName("Three").click();
        driver.findElementByName("Equals").click();

        WebElement res = driver.findElementByName("Display is  63 ");

        System.out.println(res.getText());
    }

    @AfterMethod
    public void afterTest() {
        try{
            driver.close();
            driver.quit();

      }catch (Exception ex){
          ex.printStackTrace();
      }
    }

}
