## Protractor + Cucumber

This project shows how to add Perfecto-Reporting functionality to Protractor tests using Cucumber testing framework.<br/>

Scenario's steps will be automatically logged to reporting ui as a test step.

## Getting started

Install NodeJS dependencies with the following command: <br/>
> npm install

**Configurations:**

At [protractor.conf.js](protractor.conf.js) set the following properties: 

Set your Perfecto lab user, password and host:
```JavaScript
const PerfectoUser = 'My_User';
const PerfectoPass = 'My_Pass';
const PerfectoHost = 'My_Host.perfectomobile.com';
```

Set your device capabilities:
```JavaScript
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
```

Set your Reporting test tags for easy filtering at Reporting UI :
```JavaScript
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
```

## Running the tests

Run the tests using the following command (within the same configuration file directory): 
> npm test

Or using Protractor command:
> protractor
