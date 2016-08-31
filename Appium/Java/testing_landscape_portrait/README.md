## Test Application Landscape and Portrait

If you would like to jump directly to the ***quick start*** and running the tests click [here](#quick-start) .

## AndroidDriverExtended:

[AndroidDriverExtended](src/main/java/AndroidDriverExtended.java) class extendes AndroidDriver and overrides findElement method.<br/>
The new implementation for findElement searching for an element by given locator. <br/>
**If the element not found instead of throwing an exception it will try to scroll down and retry to find the element agian.**

**Usage:**

- Pre test configurations:
```java
// Optional - Set the scroll context (swipe between scrollable elements), default value = 1
driver.setScrollableContext(1);
// Optional - Times to scroll before throwing exception ElementNotFound, default value = 2
driver.setTimesToScroll(2);
// Optional - Length of each scroll measured by JS scroll command, default value = 1
driver.setScrollLength(1);
```

**Elements to scroll on:** 

By default the new implementation of findElement method will scroll on the first element with *scrollable* attribute. <br/>
This can be changed to the 2nd, 3rd and so on... elements by changing setScrollableContext method.

- Test:
```Java
// works like the old find element 'XXX' = locator.  
 driver.findElement( By.XXX )
```

##Quick Start:
:information_source: This project uses "selendroid" application which can be found [here](https://github.com/PerfectoCode/AppsForSamples/tree/master/selendroid-test-app-0.17.0).

- Setting up the test : 

[PerfectoTestNG](src/test/java/PerfectoTestNG.java) class define the test behavior, setUp and tearDown methods which runs before each test.<br/>
In addition you will need to insert your Perfecto Lab username, password and host.
```Java
private final String Perfecto_User = "MyUser";
private final String Perfecto_Pass = "MyPass";
private final String Perfecto_Host = "MyHost.perfectomobile.com";
```

- Download the test's application from [here](https://github.com/PerfectoCode/AppsForSamples/tree/master/selendroid-test-app-0.17.0) and upload to your mobile cloud.<br/>

- Set the "NEW_APP_PATH" field to be the path of the application in your mobile cloud repository. This can be done at [PerfectoTestNG](src/test/java/PerfectoTestNG.java) class.
```Java
capabilities.setCapability("app", "NEW_APP_PATH");
```

Set your desired device capabilities at [testng.xml](testng.xml). Please note that the test uses two different devices for parallel execution.
The following field define the device mode (landscape or portrait):
```xml
<parameter name="mode" value="landscape"/>
<!-- OR -->
<parameter name="mode" value="portrait"/>
```

- Run the test from [testng.xml](testng.xml) file as TestNG test (Or directly from the test class in case you are using intellij). 

