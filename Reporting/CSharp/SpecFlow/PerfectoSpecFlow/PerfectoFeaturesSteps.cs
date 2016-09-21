using NUnit.Framework;
using TechTalk.SpecFlow;

namespace PerfectoSpecFlow
{
    [Binding]
    public class PerfectoFeaturesSteps: PerfectoHooks
    {
        [Given(@"I navigate to (.*) search page")]
        public void GivenINavigateToGoogleSearchPage(string site)
        {
            var url = string.Format("https://{0}.com", site);
            driver.Navigate().GoToUrl(url);
        }
        
        [Given(@"I search for (.*)")]
        public void GivenISearchForPerfectoCodeGitHub(string valueToSearch)
        {
            driver.FindElementByName("q").SendKeys(valueToSearch);
            driver.FindElementById("tsbb").Click();
        }
        
        [When(@"I click the first search result")]
        public void WhenIClickTheFirstSearchResult()
        {
            driver.FindElementByCssSelector("#rso > div > div:nth-child(1) > div > div > div._OXf > h3 > a").Click();
        }
        
        [Then(@"I validate that (.*) is in the page's title")]
        public void ThenIValidateThatPerfectoIsInThePageSTitle(string val)
        {
            StringAssert.Contains(val, driver.Title);
        } 
    }
}
