## webdriverio + jasmine 

This sample demonstrate adding Perfecto reporting client to webdriverio test.

## Getting started
Install NodeJS dependencies with this command:

> npm install

## Running the test
Update your Perfecto credentials in [conf.js](conf.js).
```JavaScript
//Set your Perfecto lab user, pass and host here.
const host = 'MY_HOST.perfectomobile.com';
const user = 'MY_USER';
const pass = 'MY_PASS';
```
You can run [spec.js](spec.js) with this command:

> npm test