# SWISS Airlines cross platform sample.

This test accesses SWISS Airlines' database, both via a Web app on a desktop and a hybrid app via a mobile.

It begins the process of ordering Economy flight tickets from Zurich to London on June 20th, and back on June 30th.

Two drivers are created in a single test - RemoteWebDriver and AndroidDriver, the later using both NATIVE_APP and WEBVIEW contexts. 

**To do before running this test**:
- Define your lab, username, and password.
- Define phone number of  mobile to use for this test. In this mobile install an instrumented version of SWISS Airlines app.
