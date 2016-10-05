## JBehave 

The project demonstrates adding Reportium calls to Selenium tests that are run via JBehave.

It includes a customized reporter [PerfectoReporter](src/test/java/Reporter/PerfectoReporter.java) which implements Reportium calls.<br/>
PerfectoReporter logs every scenario as a sepearate test report. Each step (Given, When, Then) logged as a test step. <br/>
After each successful scenario it will report on successful test, otherwise it will report a failure and log the failure message and exception.

In addition this project includes two scenarios:<br/>
1. First scenario should succeed to show successful test execution within reporting UI.<br/>
2. The second scenario should fail in order to show failure test execution within reporting UI.

**Adding the additional reporter:**

Use your runner class (in this sample [runner](src/test/java/runner/runner.java)) and add the following lines to the configuration method:
```Java
@Override
public Configuration configuration() {
    return new MostUsefulConfiguration()
		.useStoryLoader(
			new LoadFromClasspath(this.getClass().getClassLoader()))
		.useStoryReporterBuilder(new StoryReporterBuilder()
			.withReporters(new PerfectoReporter(driverProvider) , ...... ) //It's possible to add additional reporters
		);
}
```

:information_source: Note that driverProvider must be specify in order to user PerfectoReporter. <br/>

**Using the Driverprovider Class:**

The [DriverProvider](src/test/java/Objects/DriverProvider.java) instance holds the webdriver. <br/>
This in order to be able to get it later via PerfectoReporter and test steps implementation.<br/>
Set your Perfecto Lab User, Password, host and DesiredCapabilities at the ***initialize*** method: 
```Java
@Override
public void initialize() {
	DesiredCapabilities capabilities = new DesiredCapabilities();
	capabilities.setCapability("platformName", "Android");
	capabilities.setCapability("model", "");
	capabilities.setCapability("user", MyPerfectoUser);
	capabilities.setCapability("password", MyPerfectoPassword);

	//Other capabilities ... 

	try {
		driver = new RemoteWebDriver(new URL("http://" + MyHost + "/nexperience/perfectomobile/wd/hub"), capabilities);
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}
}
```

Initializing and closing the driver done by PerfectoSteps which extends JBehave's Steps class.<br/>
That way before each story new instance of DriverProvider with a new WebDriver created and after each it taking care of closing the driver.

**Adding ContextTags:**

ContextTags can be added to the test execution by adding `@PerfectoTags` to the Story or Scenario ***Meta*** data.<br/>
Story level tags will be added to all the tests executions within the same Story.<br/>
Scenario level tags will be added only to the particular test execution.<br/>

For example:
```Story
Meta:
@PerfectoTags Tag1, Tag2, a longer tag 			// This tags will be added to all tests executions

Scenario: Search in google Jbehave
Meta:
@PerfectoTags FirstScenario, Success 			// This tags will be added only to the particular test execution.
Given I navigate to google
When I search for PerfectoCode GitHub
Then I click the first search result
Then I validate Perfecto is in the page's title
```

**Running The Project:**<br/>
Run the [runner.java](src/test/java/runner/runner.java) file as Junit via IDE (Eclipse or Intellij).