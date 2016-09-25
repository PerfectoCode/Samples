## Reporting SpecFlow

This project demonstrates adding reporting client using [Reporting SDK for C#](https://www.nuget.org/packages/Perfecto-Reporting) to your SpecFlow tests.


:information_source: This project requires [Perfecto Reporting SDK](https://github.com/PerfectoCode/Samples/wiki) for C#.


The project contains three main files:<br/>
[PerfectoHooks](PerfectoSpecFlow/PerfectoHooks.cs) - Contains the configuration required in order to execute test using reporting client.<br/>
[PerfectoFeatures](PerfectoSpecFlow/PerfectoFeatures.feature) - Standard SpecFlow feature file, conatins BDD scenario and steps. <br/>
[PerfectoFeaturesSteps](PerfectoSpecFlow/PerfectoFeaturesSteps.cs) - C# implementation of the steps in the feature file. 

**TODO:**

- Make sure you have installed the [Perfecto plugin for Visual Studio](https://www.perfectomobile.com/ni/resources/downloads/add-ins-plugins-and-extensions) and [Reporting SDK for C#](https://www.nuget.org/packages/Perfecto-Reporting).
- Download the project and import the .sln file to Visual Studio IDE.
- Set your Perfecto lab User, Password and Host in the [PerfectoHooks](PerfectoSpecFlow/PerfectoHooks.cs) file. 

     ```Csharp
//Perfecto Lab credentials
const string PerfectoUser = "My_User";
const string PerfectoPass = "My_Pass";
const string PerfectoHost = "My_Host.perfectomobile.com"; 
     ``` 
- Run the tests as SpecFlow tests.<br/>
Once the test run is complete, the report URL can be found in the test output.<br/>
