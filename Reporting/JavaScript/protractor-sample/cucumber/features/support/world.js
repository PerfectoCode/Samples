const Reporting = require('perfecto-reporting');

module.exports = function () {

    this.setDefaultTimeout(20 * 1000);

    this.World = function World() {

        var chai = require('chai');
        var chaiAsPromised = require('chai-as-promised');

        chai.use(chaiAsPromised);
        this.expect = chai.expect;

        this.reportiumClient = setupReportingClient();

    }

    function setupReportingClient() {
        // Setup Perfecto reporting client
        const perfectoExecutionContext = new Reporting.Perfecto.PerfectoExecutionContext({
            webdriver: browser.driver,
            tags: ['daniela@perfectomobile.com', 'cucumber', 'NodeJS'] // optional
        });
        return new Reporting.Perfecto.PerfectoReportingClient(perfectoExecutionContext);
    }

}