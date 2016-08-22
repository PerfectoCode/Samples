var reportingClient;

//Set your Perfecto lab user, pass and host here.
const host = 'MY_HOST.perfectomobile.com';
const user = 'MY_USER';
const pass = 'MY_PASS';

//Define your global tags here: 
const tags = ['SampleTag1', 'SampleTag2', 'SampleTag3'];

exports.config = {
    //
    // ==================
    // Specify Test Files
    // ==================
    host: host,
    path: '/nexperience/perfectomobile/wd/hub',
    port: '80',

    specs: [
        'specs.js'
    ],
    //
    // ============
    // Capabilities
    // ============
    // Define your capabilities here. 
    maxInstances: 10,
    capabilities: [{
        // maxInstances can get overwritten per capability. 
        user: user,
        password: pass,
        platformName: 'Android',
        browserName: 'mobileOS'
    }],
    //
    // ===================
    // Test Configurations
    // ===================
    // Define all options that are relevant for the WebdriverIO instance here
    //
    // By default WebdriverIO commands are executed in a synchronous way using
    // the wdio-sync package. If you still want to run your tests in an async way
    // e.g. using promises you can set the sync option to false.
    sync: true,
    //
    // Level of logging verbosity: silent | verbose | command | data | result | error
    logLevel: 'silent',
    //
    // Enables colors for log output.
    coloredLogs: true,
    //
    // Saves a screenshot to a given path if a command fails.
    screenshotPath: './errorShots/',
    //
    // Default timeout for all waitFor* commands.
    waitforTimeout: 10000,
    //
    // Default timeout in milliseconds for request
    // if Selenium Grid doesn't send response
    connectionRetryTimeout: 90000,
    //
    // Default request retries count
    connectionRetryCount: 3,
    //
    // Make sure you have the wdio adapter package for the specific framework installed
    // before running any tests.
    framework: 'jasmine',
    //
    // Options to be passed to Jasmine.
    jasmineNodeOpts: {
        //
        // Jasmine default timeout
        defaultTimeoutInterval: 9999999,
        //
        // The Jasmine framework allows interception of each assertion in order to log the state of the application
        // or website depending on the result. For example, it is pretty handy to take a screenshot every time
        // an assertion fails.
        expectationResultHandler: function (passed, assertion) {
            // do something
        }
    },

    //
    // =====
    // Hooks
    // =====
    // Gets executed before test execution begins. At this point you can access all global
    // variables, such as `browser`. It is the perfect place to define custom commands.
    before: function (capabilities, specs) {
        const Reporting = require('perfecto-reporting');

        reportingClient = new Reporting.Perfecto.PerfectoReportingClient(new Reporting.Perfecto.PerfectoExecutionContext({
            webdriver: {
                executeScript: (command, params) => {
                    browser.execute(command, params);
                }

            },
            tags: tags
        }));
        browser.reportingClient = reportingClient;

        var myReporter = {
            specStarted: function (result) {
                reportingClient.testStart(result.fullName);
            },
            specDone: function (result) {
                if (result.status === 'failed') {
                    const failure = result.failedExpectations[result.failedExpectations.length - 1];
                    reportingClient.testStop({
                        status: Reporting.Constants.results.failed,
                        message: `${failure.message} ${failure.stack}`
                    });
                } else {
                    reportingClient.testStop({
                        status: Reporting.Constants.results.passed
                    });
                }
            }
        }

        jasmine.getEnv().addReporter(myReporter);
    },
    //
    // Hook that gets executed before the suite starts
    // beforeSuite: function (suite) {
    // },
    //
    // Hook that gets executed _before_ a hook within the suite starts (e.g. runs before calling
    // beforeEach in Mocha)
    // beforeHook: function () {
    // },
    //
    // Hook that gets executed _after_ a hook within the suite starts (e.g. runs after calling
    // afterEach in Mocha)
    // afterHook: function () {
    // },
    //
    // Function to be executed before a test (in Mocha/Jasmine) or a step (in Cucumber) starts.
    // beforeTest: function (test) {
    // },
    //
    // Runs before a WebdriverIO command gets executed.
    // beforeCommand: function (commandName, args) {
    // },
    //
    // Runs after a WebdriverIO command gets executed
    // afterCommand: function (commandName, args, result, error) {
    // },
    //
    // Function to be executed after a test (in Mocha/Jasmine) or a step (in Cucumber) starts.
    // afterTest: function (test) {
    // },
    //
    // Hook that gets executed after the suite has ended
    // afterSuite: function (suite) {
    // },
    //
    // Gets executed after all tests are done. You still have access to all global variables from
    // the test.
    after: function (result, capabilities, specs) {
        console.log('');
        var queryParameters = {
            'tags[0]': tags
        }
        var tenantId = host.split(".")[0];
        var reportUrl = require('url').resolve(`https://${tenantId}.reporting-01.perfectomobile.com`,
            '?' + require('querystring').stringify(queryParameters));
        console.log(`reportUrl = ${reportUrl}`);
    },
    //
    // Gets executed after all workers got shut down and the process is about to exit. It is not
    // possible to defer the end of the process using a promise.
    // onComplete: function (exitCode) {
    // }
}