using System;
using NUnit.Framework;
using OpenQA.Selenium.Remote;
using Reportium.client;
using Reportium.model;
using Reportium.test.Result;
using Reportium.test;

namespace ReportingTests.NUnit
{
    /// <summary>
    /// Test base class
    /// </summary>
    /// <remarks> 
    /// Create RemoteWebDriver and Reportium client object.
    /// Configure here what happensed before and after each test and
    /// before and after all tests.
    /// </remarks>
    [TestFixture]
    class PerfectoTestBox
    {
        //Perfecto lab username, password and host.
        private const string PERFECTO_USER = "MY_USER";
        private const string PERFECTO_PASS = "MY_PASS";
        private const string PERFECTO_HOST = "MY_HOST.perfectomobile.com";

        internal static RemoteWebDriver driver;
        internal static ReportiumClient reportiumClient;

        /// <summary>
        /// Setup once what happens before all tests
        /// </summary>
        [OneTimeSetUp]
        public void OneTimeSetUp()
        {
            //DesiredCapabilities capabilities = new DesiredCapabilities(browserName, string.Empty, new Platform(PlatformType.Any));
            DesiredCapabilities capabilities = new DesiredCapabilities();

            //Provide your Perfecto lab user and pass
            capabilities.SetCapability("user", PERFECTO_USER);
            capabilities.SetCapability("password", PERFECTO_PASS);

            //Device capabilities
            capabilities.SetCapability("platformName", "Android");

            //Create RemoteWebDriver
            var url = new Uri(string.Format("http://{0}/nexperience/perfectomobile/wd/hub", PERFECTO_HOST));
            driver = new RemoteWebDriver(url, capabilities);
            driver.Manage().Timeouts().ImplicitlyWait(TimeSpan.FromSeconds(15));

            //Initialize driver
            reportiumClient = clientCreator();
        }

        /// <summary>
        /// Initialize ReportiumClient with perfecto execution context. 
        /// </summary>
        /// <returns> PerfectoReportiumClient object </returns>
        private static ReportiumClient clientCreator()
        {
            PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                .withProject(new Project("My project", "2.1")) //optional 
                .withContextTags(new[] { "Tag", "Tag2" , "c#" }) //optional 
                .withJob(new Job("Job name", 12345)) //optional 
                .withWebDriver(driver)
                .build();

            return PerfectoClientFactory.createPerfectoReportiumClient(perfectoExecutionContext);
        }

        /// <summary>
        /// Setup what happens before each test
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            //Start a new reporting for the test with the full test name
            reportiumClient.testStart(TestContext.CurrentContext.Test.FullName , new TestContextTags("My First NUnit test" , "Tag1" , "Tag2"));
        }

        /// <summary>
        /// TearDown the test (each one of them)
        /// </summary>
        [TearDown]
        public void afterTest()
        {
            try
            {
                var status = TestContext.CurrentContext.Result.Outcome.Status.ToString();

                //test success, generates successful reporting
                if (status.Equals("Passed"))
                {
                    reportiumClient.testStop(TestResultFactory.createSuccess());
                }
                //test fail, generates failure repostiung
                else
                {
                    var Message = TestContext.CurrentContext.Result.Message;
                    var trace = TestContext.CurrentContext.Result.StackTrace;
                    Message = Message + ". Stack Trace:" + trace;
                    reportiumClient.testStop(TestResultFactory.createFailure(Message, null));
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }
        }

        /// <summary>
        /// Class cleanup (after all tests)
        /// </summary>
        /// <remarks> 
        /// This method runs after all the tests in the class are finished 
        /// Opens a browser with the report url and quit the driver
        /// </remarks>
        [OneTimeTearDown]
        public static void tearDown()
        {
            //retrieve the test report 
            try
            {
                driver.Close();
                var url = reportiumClient.getReportUrl();
                Console.WriteLine(url);

                //Optional open browser after test finished: 
                System.Diagnostics.Process.Start(url.ToString());
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }

            //Close connection and ends the test
            driver.Quit();
        }

    }
}
