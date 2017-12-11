import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * PerfectoTestNg
 * This class defines the test behavior by setting up the test and the driver.
 */
public class PerfectoTestNG {

    AndroidDriverExtended driver;

    private final String Perfecto_User = "MyUser";
    private final String Perfecto_Pass = "MyPass";
    private final String Perfecto_Host = "MyHost.perfectomobile.com";

    @Parameters({"platformName", "model", "browserName", "mode"})
    @BeforeTest
    public void setUp(String platformName, String model, String browserName, String mode) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //User capabilities
        capabilities.setCapability("user", Perfecto_User);
        capabilities.setCapability("password", Perfecto_Pass);

        //Device capabilities
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("model", model);
        capabilities.setCapability("browserName", browserName);
        //Optional
        //capabilities.setCapability("deviceName", "CE05160520C92B2B04");

        //App capabilities
        capabilities.setCapability("app", "PRIVATE:selendroid-test-app-0.17.0.apk");
        capabilities.setCapability("appPackage", "io.selendroid.testapp");
        capabilities.setCapability("autoLaunch", false);
        //Not necessary as this app not caching user data
        //capabilities.setCapability("fullReset", true);

        driver = new AndroidDriverExtended(new URL("http://" + Perfecto_Host + "/nexperience/perfectomobile/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        //rotate the screen for landscape testcase
        if (mode.equals("landscape")) {
            driver.rotate(ScreenOrientation.LANDSCAPE);
        }
    }

    @AfterTest
    public void tearDown() {
        try {
            driver.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
