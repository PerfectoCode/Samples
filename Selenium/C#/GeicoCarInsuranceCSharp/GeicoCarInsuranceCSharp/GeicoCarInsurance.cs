using System;
using System.Collections.Generic;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Remote;

namespace GeicoCarInsuranceCSharp
{
    /// <summary>
    /// Summary description for MobileRemoteTest
    /// </summary>
    [TestClass]
    public class GeicoCarInsurance
    {
        private RemoteWebDriver driver;

        // TODO: Set your cloud host and security token (Recommended)
        public static String TOKEN = "Your Security Token"
        public static String HOST = "MY_HOST.perfectomobile.com";

        // Old School Credentials (We suggest using Security Token)
        //public static String USER_NAME = "MY_USER";
        //public static String PASSWORD = "MY_PASSWORD";

        private static String TARGET_EXECUTION = "Desktop"; // If "Desktop" create Web Machine, else run on Mobile browser

        [TestInitialize]
        public void PerfectoOpenConnection()
        {
            // Create Remote WebDriver based on target execution platform
            if (TARGET_EXECUTION == "Desktop")
            {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.SetCapability("securityToken", TOKEN);

                // User name and Password Login
                //capabilities.SetCapability("user", USER_NAME);
                //capabilities.SetCapability("password", PASSWORD);

                // Target Web Machine configuration
                capabilities.SetCapability("platformName", "Windows");
                capabilities.SetCapability("platformVersion", "8.1");
                capabilities.SetCapability("browserName", "Chrome");
                capabilities.SetCapability("browserVersion", "54");

                var url = new Uri(string.Format("http://{0}/nexperience/perfectomobile/wd/hub", HOST));
                driver = new RemoteWebDriver(url, capabilities);
            }

            else {
                var browserName = "mobileOS";
                DesiredCapabilities capabilities = new DesiredCapabilities(browserName, string.Empty, new Platform(PlatformType.Any));
                capabilities.SetCapability("securityToken", TOKEN);

                // User name and Password Login
                //capabilities.SetCapability("user", USER_NAME);
                //capabilities.SetCapability("password", PASSWORD);

                capabilities.SetPerfectoLabExecutionId(PERFECTO_HOST);
                
                // Define device allocation timeout, in minutes
                capabilities.SetCapability("openDeviceTimeout", 5);

                // Script name
                capabilities.SetCapability("scriptName", "GeicoCarInsuranceCSharp");

                var url = new Uri(string.Format("http://{0}/nexperience/perfectomobile/wd/hub", HOST));
                driver = new RemoteWebDriver(url, capabilities);
            }

            driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(30));
            driver.Manage().Timeouts().SetPageLoadTimeout(TimeSpan.FromSeconds(30));
        }


        [TestMethod]
        public void GeicoCarInsuranceGetQuote()
        {
            // Navigate to geico.com
            driver.Navigate().GoToUrl("http://geico.com");

            // Maximize browser window on desktop
            if (TARGET_EXECUTION == "Desktop")
            {
                driver.Manage().Window.Maximize();
            }
                
            // Enter form data
            driver.FindElementById("motorcycle").Click();
            driver.FindElement(By.Id("zip")).SendKeys("01434");
            driver.FindElement(By.Id("submitButton")).Click();

            driver.FindElementByXPath("//*[@class = 'row control-item']/div/label[2]").Click();
            driver.FindElement(By.Id("firstName")).SendKeys("MyFirstName");
            driver.FindElement(By.Id("lastName")).SendKeys("MyFamilyName");
            driver.FindElement(By.Id("street")).SendKeys("My Address");
            driver.FindElementById("apt").SendKeys("");
            driver.FindElement(By.Id("date-monthdob")).SendKeys("8");
            driver.FindElement(By.Id("date-daydob")).SendKeys("3");
            driver.FindElement(By.Id("date-yeardob")).SendKeys("1981");

            driver.FindElementById("hasCycle").Click();
        }

        [TestCleanup]
        public void PerfectoCloseConnection()
        {
    
            driver.Close();

            if (TARGET_EXECUTION == "Desktop")
            {
                var parameters = new Dictionary<string, object>();
                driver.ExecuteScript("mobile:execution:close", parameters);
            }

            driver.Quit();
        }
    }
}
