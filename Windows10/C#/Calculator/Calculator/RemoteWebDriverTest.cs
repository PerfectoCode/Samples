using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Remote;

namespace Calculator
{
    /// <summary>
    /// Summary description for RemoteWebDriverTest
    /// Sample automation script that exercises the Surface Pro Calculator
    /// For programming samples and updated templates refer to the Perfecto GitHub at: https://github.com/PerfectoCode
    /// </summary>
    [TestClass]
    public class RemoteWebDriverTest
    {
        private RemoteWebDriver driver;

        [TestInitialize]
        public void PerfectoOpenConnection()
        {
            var browserName = "mobileOS";
            var host = "your cloud";
            var token = "your security token"

            //Old school credentials:
            //var user = "your user";
            //var pw = "your pw";

            DesiredCapabilities capabilities = new DesiredCapabilities(browserName, string.Empty, new Platform(PlatformType.Any));
            capabilities.setCapability("securityToken", token);

            //Old school credentials login:
            //capabilities.SetCapability("user", user);
            //capabilities.SetCapability("password", pw);

            //TODO: Provide your device ID
            capabilities.SetCapability("winAppId", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
            capabilities.SetCapability("model", "Surface Pro 4");
            capabilities.SetPerfectoLabExecutionId(host);

            var url = new Uri(string.Format("https://{0}/nexperience/perfectomobile/wd/hub", host));
            
            driver = new RemoteWebDriver(url, capabilities);
            driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(15));
            Trace.WriteLine("Driver created, execution start");
        }

        [TestCleanup]
        public void PerfectoCloseConnection()
        {
            driver.Close();
            Trace.WriteLine("Run ended report URL: " + reportUrl);
            driver.Quit();
            Trace.WriteLine("Execution completed");
        }

        [TestMethod]
        public void WebDriverTestMethod()
        {
            //Write your test here
            driver.FindElementByName("Clear").Click();
            driver.FindElementByName("Two").Click();
            driver.FindElementByName("Zero").Click();
            driver.FindElementByName("One").Click();
            driver.FindElementByName("Six").Click();
            driver.FindElementByName("Minus").Click();
            driver.FindElementByName("One").Click();
            driver.FindElementByName("Nine").Click();
            driver.FindElementByName("Five").Click();
            driver.FindElementByName("Three").Click();
            driver.FindElementByName("Equals").Click();

            IWebElement res = driver.FindElementByName("Display is  63 ");
           
            Trace.WriteLine(res.Text);
        }
    }
}
