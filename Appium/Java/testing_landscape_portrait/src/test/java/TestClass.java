import org.testng.annotations.Test;

/**
 * Test class.
 */
public class TestClass extends PerfectoTestNG {

    @Test
    public void testCase1() throws InterruptedException {

        //launch application
        driver.launchApp();

        // Configure ScrollContext for the extended findElement method

        // Optional - Set the scroll context (swipe between scrollable elements), default value = 1
        driver.setScrollableContext(1);
        // Optional - Times to scroll before throwing exception ElementNotFound, default value = 2
        driver.setTimesToScroll(2);
        // Optional - Length of each scroll measured by JS scroll command, default value = 1
        driver.setScrollLength(1);

        // click registration button using @FindBy annotation
        MainPage mainPage = new MainPage(driver);
        mainPage.clickRegistrationBTN();

        // building RegistrationPage instance, insert registration info using builder design pattern
        new RegistrationPage(driver)
                .withUsername("My_Username")
                .withEmail("My_Email@MailService.com")
                .withPassword("My_Password")
                .withName("My_Name")
                .withLanguage(ProgrammingLanguage.Javascript)
                .Verify();

        // Just a busy waiting to watch pages changed
        Thread.sleep(2000);
    }
}
