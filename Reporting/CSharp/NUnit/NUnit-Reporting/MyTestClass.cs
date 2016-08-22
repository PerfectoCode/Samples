using NUnit.Framework;

namespace ReportingTests.NUnit
{
    /// <summary>
    /// Test class
    /// </summary>
    /// <remarks> This class inherit from <see cref="PerfectoTestBox"/> </remarks>
    [TestFixture]
    class MyTestClass: PerfectoTestBox
    {
        /// <summary>
        /// Test 1 - Should Success
        /// </summary>
        /// <remarks> Navigate to google and search "PerfectoCode GitHub" and assert keyword is in the title </remarks>
        [Test]
        public void should_success()
        {
            //Test step will be shown on the report ui
            reportiumClient.testStep("Navigate to google and search PerfectoCode GitHub"); 
            driver.Navigate().GoToUrl("https://www.google.com");

            //locate the search bar and sendkeys
            driver.FindElementByName("q").SendKeys("PerfectoCode GitHub");

            //click on the search button
            driver.FindElementById("tsbb").Click();

            //Add as many test steps as you want
            reportiumClient.testStep("Choose first result and validate title"); 
            driver.FindElementByCssSelector("#rso > div > div:nth-child(1) > div > div > div._OXf > h3 > a").Click();

            //a keyword to validate
            var keyword = "Perfecto"; 

            //assert that Keyword is in the page title
            Assert.IsTrue(driver.Title.Contains(keyword));
        }

        /// <summary>
        /// Test 2- Should fail
        /// </summary>
        /// <remarks> Asserting failure </remarks>
        [Test]
        public void should_fail()
        {
            Assert.Fail("Asserting fail");
        }

    }
}
