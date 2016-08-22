# Android Keep Automation
This sample presents two versions of the same automation script. 
- _AppiumTest_ includes a sample that employs TestNG to run the automation to create three notes in Keep, two with reminders. Written using POM to encapsulate the functionality of the different app screens.
- _WithReporting_ takes the sample to the next level by incorporating the connection to the new standard Report generating tools from Perfecto.

# Changes for including Reporting
See the [wiki](https://github.com/PerfectoCode/Samples/wiki/Reporting) to get an overview of how to incorporate the Reporting tool into your code.
Specifically in this sample - the following are the changes that were applied to gain the benefits of the new Reports:

1. In the _KeepTest.java_ file, that contains the test program, we added the creation of the ReportiumClient instance after creating the Androiddriver instance. Be careful to create only a single instance for each test run.
2. In the _KeepTest.java_ file we added the @BeforeMethod and @AfterMethod methods, to start and stop the test, as described in the wiki.
3. The Page Objects use the ReportiumClient to define steps of the test in the page methods. This makes it easier to identify when the test goes from screen to screen.
4. We retrieve the URL of the report just before disconnecting from the AndroidDriver and reporting it to the user. The user can use the URL to view the report in his browser.

