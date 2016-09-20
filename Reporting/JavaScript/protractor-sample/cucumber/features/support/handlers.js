var myHandlers = function () {

    this.registerHandler('BeforeFeature', (feature, callback)=> {
        if (feature.getName() != null) {
            browser.currentFeature = feature.getName();
        }
        callback();
    });

    /**
     * BeforeStep handler.
     * Logging the step to reporting client.
     * This is a workaround to get the step name.
     */
    this.registerHandler('BeforeStep', (step, callback) => {
        if (step.getName() != null) {
            browser.reportingClient.testStep(`${step.getKeyword()} ${step.getName()}`);
        }
        callback();
    });
};

module.exports = myHandlers;