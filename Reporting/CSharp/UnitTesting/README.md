## UnitTesting

This project demonstrates adding reportium client using [Reporting SDK for C#](https://www.nuget.org/packages/Perfecto-Reporting) to your UnitTesting tests.

The project uses the standard template from the Perfecto [plugin for Visual Studio](https://community.perfectomobile.com/series/25849).

To demonstrate Perfecto Reporting functionality, this project contatins two tests, one that will result in *success* and another in *failure*.

The first test navigates to google and searches for the PerfectoCode repository in GitHub.<br/>
Then clicks on the first search result and asserts that the Keyword *Perfecto* exists in the title of the page.

The second test uses *Assert fail* to immediately fail the test using CSharp assertion. 

**TODO:**
- Make sure you have installed Perfecto [plugin for Visual Studio](https://www.perfectomobile.com/integrations/continuous-quality-integrated-visual-studio) and [Reporting SDK for C#](https://www.nuget.org/packages/Perfecto-Reporting).
- Download the project and import the .sln file to Visual Studio IDE.
- Set your Perfecto lab User, Password and Host at under *TestClass* annotation.
```Csharp
  const string PERFECTO_USER = "MY_USER";
  const string PERFECTO_PASS = "MY_PASS";
  const string PERFECTO_HOST = "MY_HOST.perfectomobile.com";
``` 
- Run the tests as UnitTesting tests with right click on the file and then run tests.

Once the test run is complete, the report URL can be found in the test output.<br/>
The following code will automatically open the URL in your default browser:<br/>
``` Csharp
  //Optional open browser after test finished : 
  System.Diagnostics.Process.Start(url.ToString());
``` 
