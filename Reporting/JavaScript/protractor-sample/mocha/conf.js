const Reporting = require('perfecto-reporting');
const inherits = require('mocha').utils.inherits;
const Base = require('mocha').reporters.Base;

var reportingClient;

const PerfectoMochaReporter = function (runner) {
  Base.call(this, runner);
  var passes = 0;
  var failures = 0;

  runner.on('test', function (test) {
    reportingClient.testStart(test.fullTitle(), ['Mocha' , 'test_integrate_mocha_reporting']);
  });

  runner.on('pass', function (test) {
    reportingClient.testStop({
      status: Reporting.Constants.results.passed
    });
  });

  runner.on('fail', function (test, err) {
    console.log(`${err.message} ${err.stack}`);
    reportingClient.testStop({
      status: Reporting.Constants.results.failed,
      message: err.message
    });
  });
}

// Custopm reporter inherit from Mocha base reporter 
inherits(PerfectoMochaReporter, Base);

exports.config = {
  //Remote address
  seleniumAddress: 'https://MY_HOST.perfectomobile.com/nexperience/perfectomobile/wd/hub',

  //Capabilities to be passed to the webdriver instance.
  capabilities: {
    browserName: 'chrome',
    user: 'MY_USER',
    password: 'MY_PASS',
    platformName: 'Android'
  },

  //Framework to use
  framework: 'mocha',

  mochaOpts: {
    reporter: PerfectoMochaReporter,
    timeout: 120000, // default suite timeout for Mocha is 2000ms. Most Selenium tests take longer
    slow: 3000
  },

  // Spec patterns are relative to the current working directly when
  // protractor is called.
  specs: ['spec.js'],

  onComplete: function () {
    // Output report URL
    return reportingClient.getReportUrl().then(
      function (url) {
        console.log(`Report url = ${url}`);
      }
    );
  },

  onPrepare: function () {
    reportingClient = new Reporting.Perfecto.PerfectoReportingClient(new Reporting.Perfecto.PerfectoExecutionContext({
      webdriver: browser.driver,
      tags: ['javascript driver']
    }));
    browser.reportingClient = reportingClient;
  }
}