// An example configuration file.

exports.config = {
//Remote address																						   
	seleniumAddress: 'https://MY_HOST.perfectomobile.com/nexperience/perfectomobile/wd/hub',

  	//Capabilities to be passed to the webdriver instance.
	capabilities: {
		browserName: 'chrome',
		user: 'MY_USER',
		password: 'MY_PASS',
		platformName: 'Android',
		//deviceName: '123456',
  	}, 

  	//Framework to use. Jasmine is recommended.
  	framework: 'jasmine',

  	// Spec patterns are relative to the current working directly when
  	// protractor is called.
  	specs: ['spec.js'],
  
	// Options to be passed to Jasmine.
	jasmineNodeOpts: {
		showColors: true,	// Use colors in the command line report.	
    	defaultTimeoutInterval: 120000	// Time to wait in ms before a test fails. Default value = 30000
  	}
}

