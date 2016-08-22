# reporting-sdk-sample
Sample project with usage examples of Perfecto's Reporting SDK.

The project uses the canonical [TodoMVC](http://todomvc.com/) as the application under test. 

Detailed instructions on running the examples can be found by framework:

- [TestNG sample](https://github.com/PerfectoCode/Samples/tree/master/Reporting/Java/testng-sample)
- [JUnit sample](https://github.com/PerfectoCode/Samples/tree/master/Reporting/Java/junit-sample)
- [Main sample](https://github.com/PerfectoCode/Samples/tree/master/Reporting/Java/main-sample)
- [Protractor + Jasmine sample](https://github.com/PerfectoCode/Samples/tree/master/Reporting/JavaScript/protractor-sample)

# Running locally during test authoring
Test authoring phase is performed using a local FirefoxDriver instance. During this phase the reporting client outputs its messages to the command line.

The choice of WebDriver to create is controlled by the _is-local-driver_ environment variable, whose default value is <code>true</code>.

In order to run the tests using a remote Selenium grid and Perfecto's Reporting solution set the variable's value to <code>false</code>

# Navigating to the generated report
A link to the generated Perfecto report can be retrieved in code:
```java
System.out.println("Report URL - " + reportiumClient.getReportUrl());
```

# Jenkins integration

## Grouping tests by job & build in Reporting
In order to view tests grouped by continuous integration system job and build, you need to provide the job name and build number into reporting as system variables.
This will enable you to group your test executions in reporting first by job name and then by build number, providing you a complete overview of the build quality.

You can also add context tags thru the JVM parameters.

If you are running your tests using Maven or Ivy, you need to add the following JVM parameters:

> -Dreportium-job-name=${JOB_NAME} -Dreportium-job-number=${BUILD_NUMBER} -Dreportium-tags=<optional tags, separated by commas>


## Adding a direct link to the report to the build page
You can add a dynamically generated link to the report for a given Jenkins job by using the [Groovy Postbuild Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Groovy+Postbuild+Plugin).
 
The script should include the following code:

```groovy
jobName = manager.build.getProject().getName()
buildNumber = manager.build.getNumber()
 
summary = manager.createSummary("graph.gif")
summary.appendText("<a href=\"https://reporting.perfectomobile.com/?TENANTID=<<my tenant name>>&jobName[0]=${jobName}&jobNumber[0]=${buildNumber}\">Perfecto Test Report</a>", false)
```


