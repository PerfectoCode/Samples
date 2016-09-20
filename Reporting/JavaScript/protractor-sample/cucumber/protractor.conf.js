const Reporting = require('perfecto-reporting');

const PerfectoUser = 'My_User';
const PerfectoPass = 'My_Pass';
const PerfectoHost = 'My_Host.perfectomobile.com';

/**
 * Protractor setup
 *
 * @type {{seleniumAddress: string, capabilities: {browserName: string, user: string, password: string, platformName: string, model: string}, getPageTimeout: number, allScriptsTimeout: number, framework: string, frameworkPath: *, specs: string[], cucumberOpts: {require: string, tags: boolean, format: string, profile: boolean, no-source: boolean}}}
 */
exports.config = {

    /**
     * Remote Address
     */
    seleniumAddress: 'http://' + PerfectoHost + '/nexperience/perfectomobile/wd/hub',

    /**
     * Desired Capabilities
     * Set here your device capabilities.
     */
    capabilities: {
        browserName: 'chrome',
        user: PerfectoUser,
        password: PerfectoPass,
        platformName: 'Android',
        model: ''
        //deviceName: '123456',
    },

    getPageTimeout: 60000,
    allScriptsTimeout: 500000,

    framework: 'custom',
    frameworkPath: require.resolve('protractor-cucumber-framework'),

    // Spec patterns are relative to the current working directly when
    // protractor is called.
    specs: ['features/*.feature'],

    cucumberOpts: {
        require: 'features',
        tags: false,
        format: 'pretty',
        profile: false,
        'no-source': true
    },

    /**
     * A callback function called once protractor is ready and available, and
     * before the specs are executed.
     *
     * At this point browser instance is available.
     * Creating reporting client instance.
     * Reporting client is available using browser.reportingClient.
     */
    onPrepare: function () {

        browser.reportingClient = new Reporting.Perfecto.PerfectoReportingClient(
            new Reporting.Perfecto.PerfectoExecutionContext({
                webdriver: browser.driver,
                tags: ['Tag 1', 'Tag 2', 'Tag 3'] // optional
            }));

    }

};
