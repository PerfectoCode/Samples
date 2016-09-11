// An example configuration file.

exports.config = {
//Remote address
    seleniumAddress: 'https://qatestlab.perfectomobile.com/nexperience/perfectomobile/wd/hub',

    //Capabilities to be passed to the webdriver instance.
    capabilities: {
        browserName: 'chrome',
        user: 'daniela@perfectomobile.com',
        password: 'Shelby1967',
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
    specs: ['features/*.Feature'],

    cucumberOpts: {
        require: 'features',
        tags: false,
        format: 'pretty',
        profile: false,
        'no-source': true
    }

}
