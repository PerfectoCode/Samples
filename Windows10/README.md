## Windows10 RemoteWebDriver Tests 

Perfecto provides support for testing of Windows 10 applications developed for the Surface Pro 3 & 4 based on Microsoft WinAppDriver.

**Using Surface Pro devices connected to the Perfecto Lab you can:**
- Install the application onto the device.
- Create your WinAppDriver test script in any supported Remote WebDriver language using the Perfecto plugin for any of the supported IDE such as Visual Studio, Eclipse or IntelliJ.

Specify the application to test using the following DesiredCapabilities:

**app**
```C#
capabilities.setCapability("app","PUBLIC:/PMTestAppWin10_1.0.7.0_Debug_Test.zip");
```
**winAppId**
```C#
capabilities.setCapability("winAppId","com.perfecto.pmtestappwin10_r8aakf30rbczp!App");
```

Read more about Windows 10 native automation in our [community](https://community.perfectomobile.com/posts/1199190-windows-10-testing-on-surface-pro) .
