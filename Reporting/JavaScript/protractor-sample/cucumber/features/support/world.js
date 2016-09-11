const Reporting = require('perfecto-reporting');

const STEP_TIMEOUT_SEC = 20;

module.exports = function () {

    this.setDefaultTimeout(STEP_TIMEOUT_SEC * 1000);

    this.World = function World() {

        var chai = require('chai');
        var chaiAsPromised = require('chai-as-promised');

        chai.use(chaiAsPromised);
        this.expect = chai.expect;

        this.reportiumClient = function setupReportingClient() {
            // Setup Perfecto reporting client
            const perfectoExecutionContext = new Reporting.Perfecto.PerfectoExecutionContext({
                webdriver: browser.driver,
                tags: ['Cucumber', 'nodejs', 'daniela@perfectomobile.com'] // optional
            });
            return new Reporting.Perfecto.PerfectoReportingClient(perfectoExecutionContext);
        }
    }

    this.Before(function(scenario){
        this.reportiumClient.testStart(scenario.getName());
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
}