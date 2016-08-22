//Create webdriver
var webdriverio = require('webdriverio');
var options = {
		//Perfecto lab user and device capabilities
        desiredCapabilities: {
             platformName: 'ANDROID',                       
             browserName: 'mobileOS',                                             
             //deviceName: 'yourDeviceID',
             user: 'MY_USER@perfectomobile.com',
             password:'MY_PASS',
    },
    host: 'demo.perfectomobile.com',
    path: '/nexperience/perfectomobile/wd/hub',
port:80 
};
 
 //Init driver, gets url and navigate
webdriverio
    .remote(options)
    .init()
    .url('http://www.google.com')
	.setValue('#lst-ib' , 'PerfectoCode GitHub')
	.click('#tsbb')
	.click('#rso > div > div:nth-child(1) > div > div > div._OXf > h3 > a')
    .getTitle().then(function(title) { //Title 
        console.log('Title was: ' + title);
    })
    .end();
	