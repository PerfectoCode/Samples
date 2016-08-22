import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.By;

//enum represent each of the main page buttons
enum mainPageBTNS {
    LogIN,
    SignUp,
    Skip
}

//Abstract class of page representation as object
abstract class Page{

    AppiumDriver driver;

    public Page(AppiumDriver driver){
        this.driver = driver;
    }
}

//main page object extends PageObject class logic
class mainPage extends Page{

    //Constructor receives from the test class Appium driver
    public mainPage(AppiumDriver driver){
        super(driver);
    }

    /**
     * Choosing one of the options from the starting page of the application
     * @param btn a button from the main page represented as enum.
     * Clicking the button, locating by xpath //*[@text = ...]
     */
    public void Choose(mainPageBTNS btn){
        By logIn = By.xpath("//*[@text = 'log in']");
        By signUp = By.xpath("//*[@text = 'sign up']");
        By skip = By.xpath("//*[@text = 'skip for now']");

        switch(btn){
            case LogIN:
                driver.findElement(logIn).click();
                break;

            case SignUp:
                driver.findElement(signUp).click();
                break;

            case Skip:
                driver.findElement(skip).click();
                break;
        }
    }
}

//Login page
class LogIn extends Page{

    private String user,pass;

    /**
     * Login page constructor
     * @param driver
     * @param user
     * @param pass
     *
     * Preform a login operation to Reddit service
     */
    public LogIn(AppiumDriver driver , String user, String pass){
        super(driver);
        this.user = user;
        this.pass = pass;
        login(); //Take care of the login operation from the constructor.
    }

    /**
     * private function running from the constructor and preforms login.
     */
    private void login(){
        By username = By.xpath("//*[@text = 'Username']");
        By password = By.xpath("//*[contains(@resource-id , 'password') and contains(@class , 'EditText')]");
        By loginBTN = By.xpath("//*[@text = 'Log in']");

        driver.findElement(username).sendKeys(this.user); //inserting username
        driver.findElement(password).sendKeys(this.pass); //inserting password
        driver.findElement(loginBTN).click(); //click login button
    }
}

//Post login page
class postLoginPage extends Page{

    //Constructor post login page (Main page loaded after login)
    public postLoginPage(AppiumDriver driver){
        super(driver);
    }

    /**
     * Searching fo a given value
     * @param toSearch a value to search
     */
    public void search(String toSearch){
        By searchBTN = By.xpath("//*[contains(@resource-id , 'search')]"); //Search button
        By searchTextBox = By.xpath("//*[contains(@resource-id , 'search_src_text')]"); //Search text box after clicking the search button

        driver.findElement(searchBTN).click(); // Click on the search button at the post login page
        driver.findElement(searchTextBox).sendKeys(toSearch); //insert the search value
        ((AndroidDriver)driver).pressKeyCode(AndroidKeyCode.ENTER);
    }

}