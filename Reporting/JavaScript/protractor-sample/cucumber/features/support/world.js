const STEP_TIMEOUT_SEC = 60; //Step timeout in second

module.exports = function () {

    this.setDefaultTimeout(STEP_TIMEOUT_SEC * 1000);

    /**
     * Create new world instance.
     * @constructor
     * Setup a new chai expect instance.
     */
    this.World = function World() {

        var chai = require('chai');
        var chaiAsPromised = require('chai-as-promised');

        chai.use(chaiAsPromised);
        this.expect = chai.expect;

    }

}