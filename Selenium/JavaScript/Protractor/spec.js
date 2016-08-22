//Test1
describe('Protractor Perfecto Demo', function() {
  it('should have a title', function(done) {
    browser.driver.get('https://www.google.com'); //Navigate to google.com
    //Locate the search box element and insert text
    //Click on search button
    browser.driver.findElement(by.name('q')).sendKeys('PerfectoCode GitHub');
    browser.driver.findElement(by.css('#tsbb > div')).click();
    //Click the first search result
    browser.driver.findElement(by.css('#rso > div > div:nth-child(1) > div > div > div._OXf > h3 > a')).click();
    //Assert that page title quals to expected title
    expect(browser.driver.getTitle()).toEqual('GitHub - PerfectoCode/Samples: Product Samples');
    done();
  });
});

//Test2
describe('Protractor Perfecto Demo Test2' , function () {
    it('should submit a form' , function (done) {
        browser.driver.get('https://www.perfectomobile.com');//Navigate to Perfecto.com

        browser.driver.findElement(by.css('body > header > div > div > ul > li > a')).click();
        //Wait for page load up
        browser.driver.sleep(5000);

        browser.driver.findElement(by.id('FirstName')).sendKeys('First Name');
        browser.driver.findElement(by.id('LastName')).sendKeys('Last Name');
        browser.driver.findElement(by.id('Company')).sendKeys('My Company');
        //Select option testing
        browser.driver.findElement(by.id('Mobile_Testing_Role__c')).click();
        browser.driver.findElement(by.css('#Mobile_Testing_Role__c > option:nth-child(3)')).click();

        browser.driver.findElement(by.id('Email')).sendKeys('MyEmail@perfectomobile.com');
        browser.driver.findElement(by.id('Phone')).sendKeys('+972123456789');
        //Select option israel
        browser.driver.findElement(by.id('Country')).click();
        browser.driver.findElement(by.css('#Country > option:nth-child(108)')).click();
        //Submit
        browser.driver.findElement(by.css('#mktoForm_1632 > div.mktoButtonRow > span > button')).click();
        done();
    }) 
});