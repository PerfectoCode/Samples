const Reporting = require('perfecto-reporting');

/**
 * Hooks determines test setup and teardown.
 * For the full hooks API reference:
 * https://github.com/cucumber/cucumber-js/blob/master/docs/support_files/hooks.md
 */
var myHooks = function () {

    /**
     * Before scenario function.
     * Logs a new test to reporting client.
     * Test named same as scenario's name.
     */
    this.Before(function (scenario, callback) {
        var tags = [];
        var tagObj = scenario.getTags();
        for(var i = 0; i < tagObj.length; i++){
            tags.push(tagObj[i].getName());
        }
        //Adding the current feature to reporting tags
        tags.push(browser.currentFeature);
        browser.reportingClient.testStart(scenario.getName(), tags);

        callback();
    });

    /**
     * After scenario function.
     * Determines if test failed or succeed
     * and log the result to reporting client.
     */
    this.After(function (scenario, callback) {
        if (scenario.isSuccessful()) {
            browser.reportingClient.testStop({
                status: Reporting.Constants.results.passed
            });
        }
        else {
            browser.reportingClient.testStop({
                status: Reporting.Constants.results.failed,
                message: JSON.stringify(scenario.getException())
            });
        }
        browser.reportingClient.getReportUrl().then((url)=>{
           console.log(`Report-url: ${url}`);
        });
        callback();
    });

};

module.exports = myHooks;