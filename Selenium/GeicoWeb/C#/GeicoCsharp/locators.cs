using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OpenQA.Selenium;
using OpenQA.Selenium.Remote;

namespace GeicoCsharp
{
    class GeicoMainPage
    {

        private readonly IWebDriver driver;

        public GeicoMainPage(IWebDriver driver)
        {
            this.driver = driver;
        }

        public IWebElement insurance_type()
        {
            return driver.FindElement(By.Id("insurancetype"));
        }

        public IWebElement type_motorcycle()
        {
            return driver.FindElement(By.Id("optionMotorcycle"));
        }

        public IWebElement zip()
        {
            return driver.FindElement(By.Id("zip"));
        }
   
        public IWebElement submit_button()
        {
            return driver.FindElement(By.Id("submitButton"));
        }

    }

    class CustumerInfoPage
    {
        private readonly IWebDriver driver;

        public CustumerInfoPage(IWebDriver driver)
        {
            this.driver = driver;
        }

        public IWebElement FirstName()
        {
            return driver.FindElement(By.Id("firstName"));
        }

        public IWebElement LastName()
        {
            return driver.FindElement(By.Id("lastName"));
        }

        public IWebElement StreedAddress()
        {
            return driver.FindElement(By.Id("street"));
        }

        public IWebElement Apt()
        {
            return driver.FindElement(By.Id("apt"));
        }

        public IWebElement ZIP()
        {
            return driver.FindElement(By.Id("zip"));
        }

        public IWebElement Birth_Day()
        {
            return driver.FindElement(By.Id("date-daydob"));
        }

        public IWebElement Birth_Month()
        {
            return driver.FindElement(By.Id("date-monthdob"));
        }

        public IWebElement Birth_Year()
        {
            return driver.FindElement(By.Id("date-yeardob"));
        }

        public IWebElement GeicoAutoInsurance_Yes()
        {
            return driver.FindElement(By.XPath("//*[@class = 'radio'][1]"));
        }

        public IWebElement GeicoAutoInsurance_No()
        {
            return driver.FindElement(By.XPath("//*[@class = 'radio'][2]"));
        }

        public IWebElement GeicoMotorcycleInsurance_Yes()
        {
            return driver.FindElement(By.XPath("//*[@id = 'hasCycle']/option[2]"));
        }

        public IWebElement GeicoMotorcycleInsurance_No()
        {
            return driver.FindElement(By.XPath("//*[@id = 'hasCycle']/option[3]"));
        }

        public IWebElement Submint_button()
        {
            return driver.FindElement(By.Id("btnSubmit"));
        }

    }
}
