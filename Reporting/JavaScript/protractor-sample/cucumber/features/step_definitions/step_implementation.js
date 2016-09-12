/**
 * Steps implementation
 *
 * Implement here the scenario's steps.
 * Protractor browser is available at this point.
 */
module.exports = function () {

    this.Given(/^I in google search page$/, function (callback) {
        browser.driver.get('http://www.google.com')
            .then(callback);
    });

    this.Then(/^I search for Perfecto-Code repository$/, function (callback) {
        browser.driver.findElement(by.name('q'))
            .sendKeys('Perfecto-Code GitHub');
        browser.driver.findElement(by.id('tsbb'))
            .click()
            .then(callback);
    });

    this.Then(/^click the first search result$/, function (callback) {
        browser.driver.findElement(by.css('#rso > div > div:nth-child(1) > div > div > div._OXf > h3 > a'))
            .click()
            .then(callback);
    });

    this.Then(/^I validate the page's title\.$/, function (callback) {
        this.expect(browser.driver.getTitle())
            .to.eventually.equal('GitHub - PerfectoCode/Samples: Product Samples');
        callback();
    });
}
