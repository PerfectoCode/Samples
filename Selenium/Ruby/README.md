## Selenium WebDriver Samples Using Ruby

These code samples show how to create Selenium tests using ruby programming language.

**Download:**
- Selenium download [here](http://www.seleniumhq.org/download/) - Choose Ruby. 
- Eclipse IDE is recommended, you can find Plugins for Ruby at the [marketplace](https://marketplace.eclipse.org/) .
- Eclipse integration with perfecto plugin download [here](https://www.perfectomobile.com/download-integrations) .

### Easy Starting: 

Setting up the webdriver: 
```Ruby
require 'selenium-webdriver'
require 'uri'

capabilities = {
  :deviceName => 'My_deviceName',
  :user => 'My_User',
  :password => 'My_Password'
}

host 	= 'My_Host.perfectomobile.com'
url 	= "http://" + host + "/nexperience/perfectomobile/wd/hub"

#Load webdriver
driver = Selenium::WebDriver.for(:remote, :url => url, :desired_capabilities => capabilities)
```
