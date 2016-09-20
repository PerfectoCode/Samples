/**
 * Steps implementation
 *
 * Implement here the scenario's steps.
 * Protractor browser is available at this point.
 */
const chai = require('chai'),
    chaiAsPromised = require('chai-as-promised'),
    STEP_TIMEOUT_SEC = 60 * 1000;

chai.use(chaiAsPromised);
expect = chai.expect;

module.exports = function () {
    this.Given(/^I'm on the google search page$/, {timeout: STEP_TIMEOUT_SEC}, () => {
        return browser.driver.get('http://www.google.com');
    });

    this.Then(/^I search for Perfecto-Code repository$/, {timeout: STEP_TIMEOUT_SEC}, () => {
        browser.driver.findElement(by.name('q'))
            .sendKeys('Perfecto-Code GitHub');
        return browser.driver.findElement(by.id('tsbb'))
            .click();
    });

    this.Then(/^click the first search result$/, {timeout: STEP_TIMEOUT_SEC}, () => {
        return browser.driver.findElement(by.partialLinkText('GitHub - PerfectoCode'))
            .click();
    });

    this.Then(/^I validate the page's title\.$/, {timeout: STEP_TIMEOUT_SEC}, ()=> {
        browser.driver.wait(() => {
            return browser.driver.getCurrentUrl()
                .then(url => url.indexOf('google') === -1);
        }, 9000)
            .then(() => expect(browser.driver.getTitle()).to.eventually.equal('GitHub - PerfectoCode/Samples: Product Samples'));
    });
};
