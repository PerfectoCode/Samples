using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Remote;


namespace RemoteExecuteJS
{
    /// <summary>
    /// Summary description for MobileRemoteTest
    /// </summary>
    [TestClass]
    public class RemoteWebDriverTest
    {
        private RemoteWebDriverExtended driver;

        [TestInitialize]
        public void PerfectoOpenConnection()
        {
            var user = "MY_USER";
            var pass = "MY_PASS";
            var host = "my_host.perfectomobile.com";

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.SetCapability("user", user);
            capabilities.SetCapability("password", pass);

            //TODO: Provide your device capabilities
            //Capabilities example
            capabilities.SetCapability("platformName", "Windows");
            capabilities.SetCapability("platformVersion", "8.1");
            capabilities.SetCapability("browserName", "Chrome");
            capabilities.SetCapability("browserVersion", "50");
            //capabilities.SetCapability("deviceName", "[ENTER YOUR DEVICE ID HERE]");


            var url = new Uri(string.Format("http://{0}/nexperience/perfectomobile/wd/hub", host));
            driver = new RemoteWebDriverExtended(new HttpAuthenticatedCommandExecutor(url), capabilities);
            driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(15));
        }

        [TestMethod]
        public void WebDriverTestMethod()
        {
            //Navigate to Amazon.com using JavaScript
            driver.ExecuteScript("window.location.href = \"https://www.amazon.com/\";");

            //verify page title
            if (!driver.Title.Equals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more"))
            {
                //if titles not equals then throw exception.
                throw new Exception("Page title are not equals!");
            }

            //Insert text 
            driver.ExecuteScript("document.getElementById(\"nav-link-yourAccount\").click();");
            driver.ExecuteScript("document.getElementById(\"createAccountSubmit\").click();");
            driver.ExecuteScript("document.getElementById(\"ap_customer_name\").value = \"Testing name\";");
            driver.ExecuteScript("document.getElementById(\"ap_email\").value = \"Testing_mail@mailservice.com\";");
            driver.ExecuteScript("document.getElementById(\"ap_password\").value = \"passTest123\";");
            driver.ExecuteScript("document.getElementById(\"ap_password_check\").value = \"passTest123\";");
            driver.ExecuteScript("document.getElementById(\"continue\").click()");
        }

        [TestCleanup]
        public void PerfectoCloseConnection()
        {
            driver.Close();
            driver.Quit();
        }

    }
}
