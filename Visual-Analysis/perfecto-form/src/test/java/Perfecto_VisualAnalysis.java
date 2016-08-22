import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Perfecto_VisualAnalysis {

    RemoteWebDriver driver;
    WebDriverWait   wait;
    String          testedBrowser;

    //TODO: Set directory path to your cloud repository (where the test's images saved at)
    final String imagePath = "PUBLIC:VisualAnalysis"; // <~ Example directory

    //TODO: Set test parameters at testng.xml
    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion", "resolution"})
    @BeforeTest
    public void beforeMethod(String platformName, String platformVersion, String browserName, String browserVersion, String resolution) throws MalformedURLException {
        System.out.println("Starting test");
        //Create and configure driver
        testedBrowser   = browserName;
        driver          = driverCreator.init_driver(platformName, platformVersion, browserName, browserVersion, resolution);
        wait            = new WebDriverWait(driver, 30);
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void test_Visual_Analysis() {
        try {
            Map<String, Object> params = new HashMap<String, Object>();

            driver.get("https://www.perfectomobile.com/offer/begin-your-free-trial");

            //Text Analysis - Search for the text "START FREE" at the right upper corner of the screen
            params.clear();
            params.put("label", "START FREE");
            driver.executeScript("mobile:button-text:click", params); //Clicks the START FREE button

            //Waiting for the CREATE YOUR FREE ACCOUNT form to be loaded
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id = 'mktoForm_1632']")));

            //Asserts the image of Free Mobile Testing appear on the screen
            params.clear();
            params.put("content", imagePath + "\\CheckPoint1(Free Mobile Testing).png");
            Object result = driver.executeScript("mobile:image:find", params);
            Assert.assertEquals(result.toString(), "true");

            //First Name
            params.clear();
            params.put("label", imagePath + "\\FullName.png");
            params.put("text", "TestUser"); //Insert the First Name TestUser
            driver.executeScript("mobile:edit-image:set", params);

            //Last Name
            params.clear();
            params.put("label", imagePath + "\\LastName.png");
            params.put("text", "PerfectoUser");
            driver.executeScript("mobile:edit-image:set", params);

            //Company
            params.clear();
            params.put("label", imagePath + "\\Company.png");
            params.put("text", "Perfecto");
            driver.executeScript("mobile:edit-image:set", params);

            //MobileRole
            Select mobileRole = new Select(driver.findElement(By.xpath("//*[@id = 'Mobile_Testing_Role__c']")));
            mobileRole.selectByValue("Testing / QA");

            //Email
            //driver.findElement(By.xpath("//*[@id = 'Email']")).sendKeys();
            params.clear();
            params.put("label", "PUBLIC:VisualAnalysis\\Email.png");
            params.put("text", "TestUser@perfectomobile.com");
            driver.executeScript("mobile:edit-image:set", params);

            //Phone
            //driver.findElement(By.xpath("//*[@id = 'Phone']")).sendKeys("0522222222");
            params.clear();
            params.put("label", "PUBLIC:VisualAnalysis\\Phone.png");
            params.put("text", "0522222222");
            driver.executeScript("mobile:edit-image:set", params);

            //Country
            Select Country = new Select(driver.findElement(By.xpath("//*[@id = 'Country']")));
            Country.selectByValue("Israel");

            //Submit the form
            driver.findElement(By.xpath("//*[text() = 'Start']")).submit();

        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    @AfterTest
    public void afterMethod() {
        try {
            driver.close();
            driver.quit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
