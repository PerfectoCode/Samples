const chai = require('chai');
var chaiAsPromised = require('chai-as-promised');

chai.use(chaiAsPromised);
var expect = chai.expect;

describe('Protractor Perfecto Demo', function () {

  it('should have a title', function () {

    browser.driver.get('https://www.google.com'); //Navigate to google.com
    
    //Locate the search box element and insert text
    //Click on search button
    browser.reportingClient.testStep('Step 1: Search');
    browser.driver.findElement(by.name('q')).sendKeys('PerfectoCode GitHub');
    browser.driver.findElement(by.css('#tsbb > div')).click();

    //Click the first search result
    browser.reportingClient.testStep('Step 2: Click repo at google.');
    browser.driver.findElement(by.css('#rso > div > div:nth-child(1) > div > div > div._OXf > h3 > a')).click();

    //Wait to locate element in order to make sure the page loaded      
    browser.driver.wait(() => {
      return browser.driver.findElement(by.id('user-content-product-samples'))
        .then(function (elem) {
          elem.click();
          return true;
        });
    }, 10000); //Wait timeout interval

    //Assert that title equals to the expected once
    browser.reportingClient.testStep('Step 3: Asserting title equals');
    expect(browser.driver.getTitle()).to.eventually.equal('GitHub - PerfectoCode/Samples: Product Samples');
  });

  //This test should fail 
  it('should fail test', function () {

    browser.driver.get('https://www.google.com'); //Navigate to google.com

    //Locate the search box element and insert text
    //Click on search button
    browser.reportingClient.testStep('Step 1: Search');
    browser.driver.findElement(by.name('q')).sendKeys('PerfectoCode GitHub');
    browser.driver.findElement(by.css('#tsbbasdasd > div')).click();

    //Click the first search result
    browser.reportingClient.testStep('Step 2: Click repo at google.');
    browser.driver.findElement(by.css('#rso > div > div:nth-child(1) > div > div > div._OXf > h3 > a')).click();
    browser.driver.sleep(5000);

    //Assert that title equals to the expected once
    //browser.reportingClient.testStep('Step 3: Asserting title equals');
    //expect(browser.driver.getTitle()).toEqual('GitHub - PerfectoCode/Samples: Product Samples');
  });

});
