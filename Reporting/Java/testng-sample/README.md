# TestNG
The project demonstrates adding Reportium calls to Selenium tests that are run via [TestNG](http://testng.org/doc/index.html).

It includes 2 sub modules which personify different use cases:

1. Adopt Reporting with minimal changes to the code base, not changing any test script.
This type of user will gain seamless breakdown into tests by automatic reporting of _test start_ and _test end_.
This use case is implemented in the _testng_listener_only_ project.
2. Full usage of Reporting API, including update of test scripts to report functional test steps.
This use case is implemented in the _testng_full_ project.

## Using all reporting capabilities
Demonstrated by _testng-full_ project.

This project uses the full capabilities of Perfecto's reporting SDK to send events for starting functional tests, the functional steps
performed by the test and the status of the test when it ends.

It includes a base test class _AbstractPerfectoSeleniumTestNG_ which handles the connection to Perfecto Reporting.
The class also implements TestNG configuration methods (beforeXXX and afterXXX methods) to automatically report test start and end, allowing
test script authors to focus on implementation of their business logic and reporting only functional steps as part of the script.

The second base class, _AbstractTodoMvcTest_, demonstrates applicative logic to keep the code [DRY](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself).

demonstrates usage of JUnit rules to create
a WebDriver instance for each class and report test start and end automatically.

The project is made up of 2 Maven profiles to demonstrate advanced features: 
- sanity - runs the tests under _com.perfecto.reporting.sample.todomvc.sanity_ package
- regression - runs the tests under _com.perfecto.reporting.sample.todomvc.regression_ package

Run tests from command line by denoting the profiles to run, e.g. from project root

> mvn clean verify -f testng-sample/testng-full/pom.xml -Pregression

The generated TestNG report is created under the respective name e.g. 

> testng-sample/testng-full/target/surefire-reports/sanity

## Using only the TestNG plugin
Demonstrated by _testng-listener-only_ project.

Perfecto's TestNg listener is a [TestNG listener](http://testng.org/doc/documentation-main.html#testng-listeners) implementation to denote test start and stop to reduce boilerplate code. 

It is ideal for users that want minimal changes to their code base, without adding the test's logical steps to the report.

This project demonstrate the usage of Perfecto's TestNG listener for seamlessly reporting test start and end events to the Reporting solution.

### Adding the listener to your project
There are several alternatives for adding TestNG listeners to your project.

This reference demonstrates two of them:

* Using the `org.testng.annotations.Listeners annotation` - see the annotation on `TodoMvcWithListenerTest` class. 
* Specifying the listener as part of the [Maven Surefire plugin](http://maven.apache.org/surefire/maven-surefire-plugin/) configuration

### Implementation requirements
The listener reports test start and end using the WebDriver instance used by the test.

In order for the listener to get that instance the test must implement the `com.perfecto.reportium.WebDriverProvider` interface.

## Reusing reporting annotations of 3rd party solutions
Perfecto reporting can reuse existing Allure annotations by automatically sending them as test steps.

You need to setup your Maven pom.xml as follows:

```xml
    <properties>
        <aspectj.version>1.8.9</aspectj.version>
    </properties>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>${maven-surefire-plugin.version}</version>
            <configuration>
                <testFailureIgnore>false</testFailureIgnore>
                <argLine>
                    -javaagent:${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar
                </argLine>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>org.aspectj</groupId>
                    <artifactId>aspectjweaver</artifactId>
                    <version>${aspectj.version}</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
```

See an example in [testng-full/pom.xml](testng-full/pom.xml)
