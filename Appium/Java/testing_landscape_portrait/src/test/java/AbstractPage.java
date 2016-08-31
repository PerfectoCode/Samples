import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * PageObject design pattern.
 * Abstract page class.
 * Define the behavior of every page.
 */
public class AbstractPage {

    AndroidDriver driver;

    public AbstractPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
    }

}

/**
 * Application's main page.
 */
class MainPage extends AbstractPage {

    private By registrationBTN = By.xpath("//*[@resource-id='io.selendroid.testapp:id/startUserRegistration']");

    public MainPage(AndroidDriver driver) {
        super(driver);
    }

    void clickRegistrationBTN() {
        driver.findElement(registrationBTN).click();
    }
}

/**
 * Enum to define programing language choice at the registration page.
 */
enum ProgrammingLanguage {
    Ruby, PHP, Scala, Javascript, Java
}

/**
 * Registration Page.
 * Insert your information for the registration using builder pattern.
 */
class RegistrationPage extends AbstractPage {

    private By username = By.xpath("//*[@resource-id=\"io.selendroid.testapp:id/inputUsername\"]");
    private By email = By.xpath("//*[@resource-id=\"io.selendroid.testapp:id/inputEmail\"]");
    private By password = By.xpath("//*[@resource-id=\"io.selendroid.testapp:id/inputPassword\"]");
    private By name = By.xpath("//*[@resource-id=\"io.selendroid.testapp:id/inputName\"]");
    private By language = By.xpath("//*[@resource-id=\"io.selendroid.testapp:id/input_preferedProgrammingLanguage\"]");
    private By adds = By.xpath("//*[@resource-id=\"io.selendroid.testapp:id/input_adds\"]");
    private By verify = By.xpath("//*[@resource-id=\"io.selendroid.testapp:id/btnRegisterUser\"]");


    public RegistrationPage(AndroidDriver driver) {
        super(driver);
    }

    private void InsertText(By locator, String textToInsert) {
        WebElement elem = driver.findElement(locator);
        elem.sendKeys(textToInsert);
        driver.hideKeyboard();
    }

    public RegistrationPage withUsername(String text) {
        InsertText(username, text);
        return this;
    }

    public RegistrationPage withEmail(String text) {
        InsertText(email, text);
        return this;
    }

    public RegistrationPage withPassword(String text) {
        InsertText(password, text);
        return this;
    }

    public RegistrationPage withName(String text) {
        InsertText(name, text);
        return this;
    }

    public RegistrationPage withLanguage(ProgrammingLanguage lang) {
        driver.findElement(language).click();
        driver.findElement(By.xpath("//*[@text='" + lang + "']")).click();
        return this;
    }

    public void Verify() {
        driver.findElement(adds).click();
        driver.findElement(verify).click();
    }

}