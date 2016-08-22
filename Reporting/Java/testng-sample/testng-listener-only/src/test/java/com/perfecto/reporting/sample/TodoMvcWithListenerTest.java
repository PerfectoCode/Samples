package com.perfecto.reporting.sample;

import com.perfecto.reportium.WebDriverProvider;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

/**
 */
@Listeners(CustomReportingTestListener.class)
public class TodoMvcWithListenerTest implements WebDriverProvider {

    private WebDriver driver;
    private TodoMvcService todoMvcService;

    public static final String IS_LOCAL_DRIVER = "is-local-driver";

    public static String USER_NAME = "MYUSER";
    public static String PASSWORD = "MYPASSWORD";
    public static String PERFECTO_HOST = "MYCOMPANY.perfectomobile.com";

    @SuppressWarnings("Duplicates")
    @BeforeClass
    public void setupDriver() throws MalformedURLException {

        // Define target mobile device
        String browserName = "mobileOS";
        DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
        capabilities.setCapability("user", USER_NAME);
        capabilities.setCapability("password", PASSWORD);

        // Define device allocation timeout, in minutes
        capabilities.setCapability("openDeviceTimeout", 5);

        // Name of script
        capabilities.setCapability("scriptName", this.getClass().getName());

        // Create Remote WebDriver
        System.out.println("Allocating Mobile device per specified capabilities");
        if (!Boolean.parseBoolean(System.getProperty(IS_LOCAL_DRIVER))) {
            driver = new RemoteWebDriver(new URL("https://" + PERFECTO_HOST + "/nexperience/perfectomobile/wd/hub"), capabilities);
        } else {
            driver = new FirefoxDriver();
        }
        todoMvcService = new TodoMvcService(driver);
    }

    @AfterClass
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeMethod
    public void navigateToApp() {
        String url = "http://todomvc.com/examples/vanillajs/";
        Reporter.log("Navigating to " + url);
        driver.get(url);

    }

    @Test(description = "Create a new todo")
    public void createTodo() {
        Reporter.log("Create new todo with");
        String namePrefix = "createTodo";
        String todoName = todoMvcService.createUniqueTodo(namePrefix);
        Reporter.log("Created new todo called " + todoName);
        Assert.assertTrue(todoName.startsWith(namePrefix), "Unique todo name is expected to start with the given prefix " + namePrefix);

        Reporter.log("Verify todo with name " + todoName + " was added to list");
        todoMvcService.verifyAddedTodo(todoName);
    }

    @Test(description = "Create a new todo and delete it")
    public void deleteTodo() {
        Reporter.log("Create new todo");
        String todoName = todoMvcService.createUniqueTodo("deleteTodo");

        Reporter.log("Remove created todo called " + todoName);
        todoMvcService.removeTodo(todoName);
    }

    @Test(description = "Create a new todo, makr it as complete and then delete it")
    public void completeTodo() {
        Reporter.log("Create new todo");
        String todoName = todoMvcService.createUniqueTodo("deleteTodo");

        Reporter.log("Complete todo called " + todoName);
        todoMvcService.completeTodo(todoName);

        Reporter.log("Verify todo called " + todoName + " is completed");
        todoMvcService.verifyCompletedTodo(todoName);

        Reporter.log("Remove created todo called " + todoName);
        todoMvcService.removeTodo(todoName);
    }

    @Override
    public WebDriver getWebDriver() {
        return driver;
    }
}
