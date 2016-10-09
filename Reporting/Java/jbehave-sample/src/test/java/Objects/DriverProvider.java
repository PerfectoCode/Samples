package Objects;

import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverProvider implements WebDriverProvider {

    private WebDriver driver;

    private final String MyPerfectoUser = "My_User";
    private final String MyPerfectoPassword = "My_Pass";
    private final String MyHost = "My_Host.perfectomobile.com";

    @Override
    public WebDriver get() {
        return driver;
    }

    @Override
    public void initialize() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("model", "Galaxy S5");
        capabilities.setCapability("user", MyPerfectoUser);
        capabilities.setCapability("password", MyPerfectoPassword);

        //Other capabilities ...

        try {
            driver = new RemoteWebDriver(new URL("http://" + MyHost + "/nexperience/perfectomobile/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean saveScreenshotTo(String s) {
        return false;
    }

    @Override
    public void end() {
        driver.close();
        driver.quit();
    }
}
