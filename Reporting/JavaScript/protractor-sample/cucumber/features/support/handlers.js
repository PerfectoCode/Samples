
var myHandlers = function () {

    //Handler for BeforeStep Event
    this.registerHandler('BeforeStep', function (Step, callback) {
        browser.stepName = Step.getName();
        callback();
    });
}

module.exports = myHandlers;