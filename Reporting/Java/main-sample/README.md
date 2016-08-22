# Main Sample - Before & After Reporting

:information_source: This test requires the IMDb application, which can be found [here](https://github.com/PerfectoCode/AppsForSamples/tree/master/PerfectoCommunity).

The Appium code sample includes: installing the Perfecto Community native app on an Android device (from the Lab repository), navigating within the app, closing and uninstalling the app.

- The test before includes the test without the Reporting components, downloading the single test report as a PDF. 
- The test after includes the same test with the Reporting components.

Before running, make sure to:<br/>
- update the Lab credentials in the test
- register to the [Perfecto community](https://community.perfectomobile.com/)
- update the community credentials in the test

:information_source: This test is based on the Perfecto Community Android application.</br>
The app is available in the Perfecto Lab repository using this path: `PUBLIC:Android/android.perfecto.apk`. The app is also available here for download. In case this app is not available in your Lab, you can upload it and update the file path accordingly.

```java
// Install Perfecto app
capabilities.setCapability("app", "PUBLIC:Android/android.perfecto.apk");
```
