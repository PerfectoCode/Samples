using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Threading;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Remote;

namespace GoogleSearchAndFillForm
{

    [TestClass]
    public class RemoteWebDriverTest
    {
        private RemoteWebDriverExtended driver;

        [TestInitialize]
        public void PerfectoOpenConnection()
        {
        	
            var browserName = "mobileOS";
	    //TODO: Provide you cloud host, user and password.
            var host = "My_Host.perfectomobile.com";
	    var user = "My_User";
	    var pass = "My_Password";

            DesiredCapabilities capabilities = new DesiredCapabilities(browserName, string.Empty, new Platform(PlatformType.Any));
            capabilities.SetCapability("user", user);
            capabilities.SetCapability("password", pass);

            //TODO: Provide your device capabilities.
			//Capabilities for example: 
            capabilities.SetCapability("platformName", "Windows");
            capabilities.SetCapability("platformVersion", "7");
            capabilities.SetCapability("browserName", "Chrome");
            capabilities.SetCapability("browserVersion", "49");

            capabilities.SetPerfectoLabExecutionId(host);

            var url = new Uri(string.Format("http://{0}/nexperience/perfectomobile/wd/hub", host));
            driver = new RemoteWebDriverExtended(new HttpAuthenticatedCommandExecutor(url), capabilities);

            driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(15));
            driver.Manage().Window.Maximize();
        }

        /**
        * scenario:
        * 1. Open https://google.com .
        * 2. Type perfectomobile and click search.
        * 3. Press on the first search result.
        * 4. Click on START FREE btn on perfecto site.
        * 5. Fill the form and finish.
        **/
        [TestMethod]
        public void WebDriverTestMethod()
        {
            try
            {
                //Search in google.
                driver.Navigate().GoToUrl("https://google.com");
                driver.FindElementByName("q").SendKeys("perfecto mobile"); //Insert perfecto mobile.
                driver.FindElementByXPath("//*[@id='rso']/div[1]/div[1]/div/h3/a").Click();// Click the first search result.

                //Fills a form on perfecto website.
                driver.FindElementByXPath("//*[text() = 'Start Free']").Click();
                driver.FindElementById("FirstName").SendKeys("MyFirstName");
                driver.FindElementById("LastName").SendKeys("MyLaseName");
                driver.FindElementById("Company").SendKeys("MyCompany");
                driver.FindElementById("Mobile_Testing_Role__c").Click();
                driver.FindElementByXPath("//*[@value = 'Development']").Click();
                driver.FindElementById("Email").SendKeys("MyEmail@Somehost.com");
                driver.FindElementById("Phone").SendKeys("123456789");
                driver.FindElementById("Country").Click();
                driver.FindElementByXPath("//*[@value = 'Israel']").Click();
            }
            catch (Exception ex)
            {
                Trace.WriteLine(string.Format("Error getting test logs: {0}", ex.Message));
            }
        }

        [TestCleanup]
        public void PerfectoCloseConnection()
        {
            // Retrieve the URL of the Single Test Report, can be saved to your execution summary and used to download the report at a later point
            string reportUrl = (string)(driver.Capabilities.GetCapability(WindTunnelUtils.SINGLE_TEST_REPORT_URL_CAPABILITY));

            driver.Close();

            // In case you want to download the report or the report attachments, do it here.
            //try
            //{
            //    driver.DownloadReport(DownloadReportTypes.pdf, "C:\\test\\report");
            //    driver.DownloadAttachment(DownloadAttachmentTypes.video, "C:\\test\\report\\video", "flv");
            //    driver.DownloadAttachment(DownloadAttachmentTypes.image, "C:\\test\\report\\images", "jpg");
            //}
            //catch (Exception ex)
            //{
            //    Trace.WriteLine(string.Format("Error getting test logs: {0}", ex.Message));
            //}

            driver.Quit();
        }


    }
}
