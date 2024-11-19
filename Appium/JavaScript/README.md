## WebDriverIO - JavaScript Sample for Native Automation

The following sample shows how to Install an application and use WebDriverIO to automate and test it.<br/>
It uses selendroid test application which can be downloaded from [here](https://github.com/PerfectoCode/AppsForSamples/tree/master/selendroid-test-app-0.17.0).

The sample uses [WebDriverIO](http://webdriver.io/) and [Jasmine](jasmine.github.io) testing framework. 

## Getting Started: 
- use `npm install` command within the sample's directory to download the project's dependencies. 
- Set your Perfecto Lab User, Password and Host (cloud) within the file [wdio.conf.js](wdio.conf.js):
```JavaScript
//Set your Perfecto lab user, pass and host here.
const host = 'MY_HOST.perfectomobile.com';
const user = 'MY_USER@perfectomobile.com';
const pass = 'MY_PASS';
```
- Set your capabilities within the sample file: 
```JavaScript
    capabilities: [{
        // maxInstances can get overwritten per capability. 
        platformName: 'Android',                       
        browserName: 'mobileOS',                                             
        // deviceName: 'DEVICE_ID', // Optional 
        user: user,
        password: pass,
        app: 'PRIVATE:selendroid-test-app-0.17.0.apk', // Sample app to install
        appPackage: 'io.selendroid.testapp', // app's package
    }],
```
- Run the test using `npm test` command.

## Writing your test: 
- Write your test within "describe" and "it" functions: 
```JavaScript
describe('Perfecto Native NodeJS - Selendroid app', function () {

	it('Test Native and Webview', function () {

        // Your test should go here

    });
});
```

- Using assertions: 
```JavaScript
expect(Value1).toContain(Value2);
```

For the complete WebDriverIO API click [here](http://webdriver.io/api.html).
