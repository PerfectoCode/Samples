## Selenium webdriver samples using C# 

These code samples show how to use Selenium webdriver using C# and Nunit testing framework.<br/>
Perfecto has a rich support for Selenium API in C# and Visual Studio IDE. 

**Downloads:**
- Official selenium [download](http://www.seleniumhq.org/download/) - Choose C# . 
- Download Perfecto plugin for Visual Studio and .Net develpoing environment [here](https://www.perfectomobile.com/download-integrations). 

### Starting steps:
It's recommended to use Perfecto Project for Selenium test! 

Using Selenium in your C# test: 
```C#
using OpenQA.Selenium;
using OpenQA.Selenium.Firefox; //For local use .
using OpenQA.Selenium.Support.UI;
```

Creating RemoteWebDriver: 
```C#
//Add your Perfecto cloud URL
public static String PERFECTO_HOST = "MY_HOST.perfectomobile.com";

//Set your DesiredCapabilities
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.SetCapability("user", USER_NAME);
capabilities.SetCapability("password", PASSWORD);

//Create a webdriver
var url = new Uri(string.Format("http://{0}/nexperience/perfectomobile/wd/hub", PERFECTO_HOST));
driver = new RemoteWebDriverExtended(new HttpAuthenticatedCommandExecutor(url), capabilities);
```
