# JUnit
The project demonstrates adding Reportium calls to Selenium tests that are run via [JUnit](http://junit.org/junit4/).

The project includes a base test class _AbstractPerfectoSeleniumTestJunit_ which demonstrates usage of JUnit rules to create
a WebDriver instance for each class and report test start and end automatically.
 
the purpose of using this base class is to allow test script authors to focus on implementing business logic tests and report the functional steps.

Run tests from command line by running the following command from project root

> mvn clean verify -f junit-sample/pom.xml
