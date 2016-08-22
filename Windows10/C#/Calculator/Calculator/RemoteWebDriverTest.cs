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
        private RemoteWebDriverExtended driver;

        [TestInitialize]
        public void PerfectoOpenConnection()
        {
            var browserName = "mobileOS";
            var host = "your cloud";
            var user = "your user";
            var pw = "your pw";

            DesiredCapabilities capabilities = new DesiredCapabilities(browserName, string.Empty, new Platform(PlatformType.Any));
            capabilities.SetCapability("user", user);

            //TODO: Provide your password
            capabilities.SetCapability("password", pw);

            //TODO: Provide your device ID
            capabilities.SetCapability("winAppId", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
            capabilities.SetCapability("model", "Surface Pro 4");
            capabilities.SetPerfectoLabExecutionId(host);

            var url = new Uri(string.Format("https://{0}/nexperience/perfectomobile/wd/hub", host));
            
            driver = new RemoteWebDriverExtended(new HttpAuthenticatedCommandExecutor(url), capabilities);
            driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(15));
            Trace.WriteLine("Driver created, execution start");
        }

        [TestCleanup]
        public void PerfectoCloseConnection()
        {
            // Retrieve the URL of the Single Test Report, can be saved to your execution summary and used to download the report at a later point
            string reportUrl = (string)(driver.Capabilities.GetCapability(WindTunnelUtils.SINGLE_TEST_REPORT_URL_CAPABILITY));

            driver.Close();

            // In case you want to download the report or the report attachments, do it here.
            try
            {
                Dictionary<string, object> parm = new Dictionary<string, object>();
                driver.ExecuteScript("mobile:execution:close", parm);
                driver.DownloadReport(DownloadReportTypes.pdf, "C:\\test\\reportSurface");
            }
            catch (Exception ex)
            {
                Trace.WriteLine(string.Format("Error getting test logs: {0}", ex.Message));
            }

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
