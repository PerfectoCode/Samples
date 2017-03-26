using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium.Remote;
using System;

//Reportium required using statements 
using Reportium.test;
using Reportium.test.Result;
using Reportium.client;
using Reportium.model;

namespace ReportingTests
{
    /// <summary>
    /// RemoteWebDriver test class. 
    /// </summary>
    /// <remarks>
    /// This sample shows how to use Perfecto Reporting
    /// in order to generate a report for your c# unittesting tests.
    /// </remarks>
    [TestClass]
    public class RemoteWebDriverTest
    {

        const string PERFECTO_USER = "MY_USER";
        const string PERFECTO_PASS = "MY_PASS";
        const string PERFECTO_HOST = "MY_HOST.perfectomobile.com";

        private static RemoteWebDriver driver;
        private static TestContext testContextInstance;
        private static ReportiumClient reportiumClient;

        /// <summary>
        /// Class initializer 
        /// </summary>
        /// <remarks> Run before all tests in the same class only once </remarks>
        [ClassInitialize]
        public static void beforeClass(TestContext testContextInstance)
        {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            //Provide your Perfecto lab user and pass
            capabilities.SetCapability("user", PERFECTO_USER);
            capabilities.SetCapability("password", PERFECTO_PASS);

            //Provide your desired device capabilities
            capabilities.SetCapability("platformName", "Android");
            //capabilities.SetCapability("platformVersion", "platformVersion");
            //capabilities.SetCapability("manufacturer", "manufacturer");
            //capabilities.SetCapability("model", "model");
            //capabilities.SetCapability("location", "location");
            //capabilities.SetCapability("resolution", "resolution");
            //capabilities.SetCapability("description", "description");

            //Create RemoteWebDriver
            var url = new Uri(string.Format("http://{0}/nexperience/perfectomobile/wd/hub", PERFECTO_HOST));
            driver = new RemoteWebDriver(url, capabilities);
            driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(15));

            //Initialize Reportium Client
            reportiumClient = CreateReportingClient();
        }

        /// <summary>
        /// Creates perfecto reporting client
        /// </summary>
        /// <remarks> 
        /// Use costumize project name, job name and context tags.
        /// This will help you to filter your test at Perfecto reporting ui!
        /// </remarks>
        /// <returns> PerfectoReportiumClient instance </returns>
        private static ReportiumClient CreateReportingClient()
        {
            PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("My first project", "v1.0")) //optional 
                .withContextTags(new[] { "sample tag1", "sample tag2", "c#" }) //optional 
                .withJob(new Job("Job name", 12345)) //optional 
                .withWebDriver(driver)
                .build();
            return PerfectoClientFactory.createPerfectoReportiumClient(perfectoExecutionContext);
        }

        //Test 1 should success
        [TestMethod]
        public void RemoteWebDriverExtendedTest()
        {
            //Starting a test with the following context tags.
            reportiumClient.testStart("Reportium C# Test should success", new TestContextTags("test 1", "c#", "should success"));

            reportiumClient.testStep("Navigate to google and search PerfectoCode GitHub"); //Test step will be shown on the report ui
            driver.Navigate().GoToUrl("https://www.google.com");

            //locate the search bar and sendkeys
            driver.FindElementByName("q").SendKeys("PerfectoCode GitHub");

            //click on the search button
            driver.FindElementById("tsbb").Click();

            reportiumClient.testStep("Choose first result and validate title"); //Add as many test steps as you want
            driver.FindElementByCssSelector("#rso > div > div:nth-child(1) > div > div > div._OXf > h3 > a").Click();

            var keyword = "Perfecto"; //a keyword to validate

            //assert that Keyword is in the page title
            Assert.IsTrue(driver.Title.Contains(keyword));
        }

        //Test 2 should fail
        [TestMethod]
        public void WebDriverFail()
        {
            //Starting a new test with the following name and context tags
            reportiumClient.testStart("Reportium C# Test should fail", new TestContextTags("test 2", "c#" , "should fail"));

            //Asserting failure of the test
            Assert.Fail("Asserts failure");
        }

        /// <summary>
        /// After test method
        /// </summary>
        /// <remarks> This method runs after each test </remarks>
        [TestCleanup]
        public void afterTest()
        {
            try
            {
                //test success, generates successful reporting 
                if (testContextInstance.CurrentTestOutcome == UnitTestOutcome.Passed)
                {
                    reportiumClient.testStop(TestResultFactory.createSuccess());
                }
                //test fail, generates failure repostiung
                else
                {
                    reportiumClient.testStop(TestResultFactory.createFailure("Test Failed", null));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }
        }

        /// <summary>
        /// Class cleanup
        /// </summary>
        /// <remarks> this method runs after all the tests in the class are finished </remarks>
        [ClassCleanup]
        public static void classCleanUpMethod()
        {
            //retrieve the test report 
            try
            {
                var url = reportiumClient.getReportUrl();

                driver.Close();
                Console.WriteLine(url);

                //Optional open browser after test finished : 
                System.Diagnostics.Process.Start(url.ToString());
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }

            //Close connection and ends the test
            driver.Quit();
        }

        //TestContext instance, required in order to get test result
        public TestContext TestContext
        {
            get
            {
                return testContextInstance;
            }
            set
            {
                testContextInstance = value;
            }
        }

    }

}
