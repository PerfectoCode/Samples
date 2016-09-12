const Reporting = require('perfecto-reporting');

var myHooks = function () {

    //Before each scenario
    this.Before(function (scenario, callback) {
        this.reportiumClient.testStart(scenario.getName());
        callback();
    });

    this.After(function (scenario) {
        if (scenario.isSuccessful()) {
            this.reportiumClient.testStop({
                status: Reporting.Constants.results.passed
            });
        }
        else {
            this.reportiumClient.testStop({
                status: Reporting.Constants.results.failed,
                message: JSON.stringify(scenario.getException())
            });
        }
    });

};

module.exports = myHooks;