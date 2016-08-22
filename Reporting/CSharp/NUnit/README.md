## NUnit

This project demonstrates adding reportium client using [Reporting SDK for C#](https://www.nuget.org/packages/Perfecto-Reporting) to your NUnit tests.

To demonstrate Perfecto Reporting functionality, this project contatins two tests, one that will result in *success* and another in *failure*.

The project seperated into two files the first file: [PerfectoTestBox.cs](PerfectoTestBox.cs) which determines the test configuration.
At this file you will find the following properties:<br/>
`[OneTimeSetUp]` - Run before all the tests in the class.<br/>
`[SetUp]` - Run before each test. <br/>
`[TearDown]` - Run after each test.<br/>
`[OneTimeTearDown]` - Run after all tests completed.

The second file [MyTestClass.cs](MyTestClass.cs) include the tests : <br/>
The first test navigates to google and searches for the PerfectoCode repository in GitHub.<br/>
Then clicks on the first search result and asserts that the Keyword *Perfecto* exists in the title of the page.

The second test uses *Assert fail* to immediately fail the test using CSharp assertion. 

**TODO:**
- Make sure you have installed Perfecto [plugin for Visual Studio](https://www.perfectomobile.com/integrations/continuous-quality-integrated-visual-studio) and [Reporting SDK for C#](https://www.nuget.org/packages/Perfecto-Reporting).
- Download the project and import the .sln file to Visual Studio IDE.
- Set your Perfecto lab User, Password and Host in the [PerfectoTestBox.cs](PerfectoTestBox.cs) file under *TestFixture* annotation.
```Csharp
  const string PERFECTO_USER = "MY_USER";
  const string PERFECTO_PASS = "MY_PASS";
  const string PERFECTO_HOST = "MY_HOST.perfectomobile.com";
``` 
- Run the tests as [NUnit tests](https://www.nuget.org/packages/NUnit/) with the [test explorer](https://msdn.microsoft.com/en-us/library/hh270865.aspx) in Visual Studio.

Once the test run is complete, the report URL can be found in the test output.<br/>
The following code in the [PerfectoTestBox.cs](PerfectoTestBox.cs) file under *OneTimeTearDown* annotation automatically open the URL in your default browser:<br/>
``` Csharp
  //Optional open browser after test finished : 
  System.Diagnostics.Process.Start(url.ToString());
``` 
