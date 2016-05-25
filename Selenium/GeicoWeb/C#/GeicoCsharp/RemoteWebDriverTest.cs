using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Remote;

namespace GeicoCsharp
{

    [TestClass]
    public class RemoteWebDriverTest
    {
        private RemoteWebDriverExtended driver;

        [TestInitialize]
        public void PerfectoOpenConnection()
        {
            var host = "My_Host.perfectomobile.com";
            var user = "My_User@perfectomobile.com";
            var pass = "My_Pass";

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.SetCapability("user", user);
            capabilities.SetCapability("password", pass);
            
            capabilities.SetCapability("platformName", "Windows");
            capabilities.SetCapability("platformVersion", "8.1");
            capabilities.SetCapability("browserName", "Chrome");
            capabilities.SetCapability("browserVersion", "49");
            capabilities.SetCapability("resolution", "1366x768");

            var url = new Uri("http://" + host + "/nexperience/perfectomobile/wd/hub");
            driver = new RemoteWebDriverExtended(new HttpAuthenticatedCommandExecutor(url), capabilities);
            driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(25));
        }

        [TestCleanup]
        public void PerfectoCloseConnection()
        {
            try
            {
                driver.Close();
                //In case you would like download the report enable the following script:
                var parameters = new Dictionary<string, object>();
                driver.ExecuteScript("mobile:execution:close", parameters);
                driver.DownloadReport(DownloadReportTypes.pdf, "C:/test/report");
                //End report section.

            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            finally 
            { 
                driver.Quit();
            }
        }

        [TestMethod]
        public void WebDriverTestMethod()
        {
            try {
                driver.Navigate().GoToUrl("http://geico.com");

                //Main page
                GeicoMainPage main_page = new GeicoMainPage(driver);
                main_page.insurance_type().Click();
                main_page.type_motorcycle().Click();
                main_page.zip().SendKeys("1234");
                main_page.submit_button().Click();

                //Costumer Information Page.
                CustumerInfoPage custumer_info_page = new CustumerInfoPage(driver);
                custumer_info_page.FirstName().SendKeys("Daniel");
                custumer_info_page.LastName().SendKeys("Alfasi");
                custumer_info_page.StreedAddress().SendKeys("Amal 13");
                custumer_info_page.Apt().SendKeys("123");
                custumer_info_page.ZIP().Clear();
                custumer_info_page.ZIP().SendKeys("50840");
                custumer_info_page.Birth_Day().SendKeys("23");
                custumer_info_page.Birth_Month().SendKeys("8");
                custumer_info_page.Birth_Year().SendKeys("1992");
                custumer_info_page.GeicoAutoInsurance_No().Click();
                custumer_info_page.GeicoMotorcycleInsurance_No().Click();
                custumer_info_page.Submint_button().Click();
            }
            catch(Exception e)
            {
                Console.WriteLine(e);
            }

        }
    }
}
