const Reporting = require('perfecto-reporting');

var myHooks = function () {

    /**
     * Before scenario function.
     * Logs a new test to reporting client.
     * Test named same as scenario's name.
     */
    this.Before(function (scenario, callback) {
        browser.reportingClient.testStart(scenario.getName());
        callback();
    });

    // this.BeforeStep(function(Step, callback){
    //     browser.reportingClient.testStep(Step.getName());
    //     callback();
    // });

    /**
     * After scenario function.
     * Determines if test failed or succeed
     * and log the result to reporting client.
     */
    this.After(function (scenario) {
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
    });

};

module.exports = myHooks;