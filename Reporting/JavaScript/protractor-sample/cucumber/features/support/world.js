
const STEP_TIMEOUT_SEC = 60; //Step timeout in second

module.exports = function () {

    this.setDefaultTimeout(STEP_TIMEOUT_SEC * 1000);

    /**
     * Create new world instance.
     * @constructor
     * Setup a new chai expect instance and reportingClient.
     */
    this.World = function World() {

        var chai = require('chai');
        var chaiAsPromised = require('chai-as-promised');

        chai.use(chaiAsPromised);
        this.expect = chai.expect;

        // this.reportiumClient = setupReportingClient();

    }

    // /**
    //  * Create a new reporting client instance.
    //  * Optional to set test tags.
    //  */
    // function setupReportingClient() {
    //     // Setup Perfecto reporting client
    //     const perfectoExecutionContext = new Reporting.Perfecto.PerfectoExecutionContext({
    //         webdriver: browser.driver,
    //         tags: ['daniela@perfectomobile.com', 'cucumber', 'NodeJS'] // optional
    //     });
    //     return new Reporting.Perfecto.PerfectoReportingClient(perfectoExecutionContext);
    // }

}