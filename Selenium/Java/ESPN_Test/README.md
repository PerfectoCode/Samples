## Selenium code sample using ESPN website

This code sample shows how to create a multiple test execution, running on multiple devices in parallel.
It includes testNG testing framework, Maven and Reportium. 

**TODO:**
- Import the project to Eclipse or IntelliJ as a Maven project.
- Set your Perfecto lab credentials and lab name in the [Test_Main.java file](https://github.com/PerfectoCode/Samples/blob/master/Selenium/ESPN_Test/src/test/java/Test_Main.java).
- Set your device capabilities in the [testng.xml file](https://github.com/PerfectoCode/Samples/blob/master/Selenium/ESPN_Test/testng.xml).<br/>
    DO NOT CHANGE THE DEVICE LOCATION (The scenario is designed only for the ESPN global website:exclamation:).
- Set your ESPN email and password (Register at www.espn.go.com).
- Execute from [testng.xml file](https://github.com/PerfectoCode/Samples/blob/master/Selenium/ESPN_Test/testng.xml) as TestNG test.

:information_source: To change the number of times the test will run, update the test anotation parameter `invocationCount` in [Test_Main.java](https://github.com/PerfectoCode/Samples/blob/master/Selenium/ESPN_Test/src/test/java/Test_Main.java).

```java
	
    @Test(invocationCount = 1)
    public void testGroup(){
        test();
        test2();
    }
```
