
var myHandlers = function () {

    /**
     * BeforeStep handler.
     * Logging the step to reporting client.
     * This is a workaround to get the step name.
     */
    this.registerHandler('BeforeStep', function (Step, callback) {
        if(Step.getName() != null) {
            browser.reportingClient.testStep(Step.getName());
        }
        callback();
    });
}

module.exports = myHandlers;