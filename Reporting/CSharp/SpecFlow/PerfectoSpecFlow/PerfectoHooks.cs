using OpenQA.Selenium;
using OpenQA.Selenium.Appium;
using OpenQA.Selenium.Appium.Android;
using OpenQA.Selenium.Appium.iOS;
using OpenQA.Selenium.Remote;
using Reportium.client;
using Reportium.model;
using Reportium.test;
using Reportium.test.Result;
using System;
using TechTalk.SpecFlow;

namespace PerfectoSpecFlow
{
    [Binding]
    public class PerfectoHooks
    {
        protected static AppiumDriver<IWebElement> driver;
        private static ReportiumClient reportingClient;

        //Perfecto Lab credentials
        const string PerfectoUser = "My_User";
        const string PerfectoPass = "My_Pass";
        const string PerfectoHost = "My_Host.perfectomobile.com"; 
        
        /**
         * BeforeFeature method 
         * Creating appium driver and reporting client.
         **/  
        [BeforeFeature]
        public static void beforeFeature()
        {

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.SetCapability("user", PerfectoUser);
            capabilities.SetCapability("password", PerfectoPass);

            capabilities.SetCapability("platformName", "Android");
            capabilities.SetCapability("model", "Galaxy S5");
            //capabilities.SetCapability("platformVersion", "");
            //capabilities.SetCapability("browserName", "");
            //capabilities.SetCapability("deviceName", "");

            var url = new Uri(string.Format("http://{0}/nexperience/perfectomobile/wd/hub", PerfectoHost));

            if (capabilities.GetCapability("platformName").Equals("Android"))
            {
                driver = new AndroidDriver<IWebElement>(url, capabilities);
            }
            else
            {
                driver = new IOSDriver<IWebElement>(url, capabilities);
            }

            reportingClient = CreateReportingClient();
        }

        /**
         * BeforeScenario
         * Starting a new test agiants reporting server.
         **/ 
        [BeforeScenario]
        public static void beforeScenario()
        {
            reportingClient.testStart(ScenarioContext.Current.ScenarioInfo.Title, new TestContextTags(ScenarioContext.Current.ScenarioInfo.Tags));
        }

        /**
         * BeforeStep
         * Logging a new test step to reporting client.
         **/ 
        [BeforeStep]
        public static void beforeStep()
        {
            reportingClient.testStep(ScenarioStepContext.Current.StepInfo.Text);
        }

        /**
         * AfterScenario
         * Stoping the test and report to reporting server the test's status.
         * If test failed providing the exception.
         **/
        [AfterScenario]
        public static void afterScenario()
        {
            Exception scenarioExpection = ScenarioContext.Current.TestError;

            if (scenarioExpection == null)
            {
                reportingClient.testStop(TestResultFactory.createSuccess());
            }
            else
            {
                reportingClient.testStop(TestResultFactory.createFailure(scenarioExpection.Message, scenarioExpection));
            }
        }

        /**
         * Closing the driver and providing the test's URL for review. 
         **/ 
        [AfterFeature]
        public static void afterFeature()
        {
            Console.WriteLine("Report-Url: " + reportingClient.getReportUrl());
            driver.Close();
            driver.Quit();
        }

        private static ReportiumClient CreateReportingClient()
        {
            PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("My first project", "v1.0")) //optional 
                .withContextTags(new[] { "tag1", "tag2", "tag3" }) //optional 
                .withJob(new Job("Job name", 12345)) //optional 
                .withWebDriver(driver)
                .build();
            return PerfectoClientFactory.createPerfectoReportiumClient(perfectoExecutionContext);
        }

    }
}
