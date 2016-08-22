## Reddit - Appium

This code sample examine executing of Appium test in Java using TestNG + Maven.<br/>
The test implements [PageObject](https://community.perfectomobile.com/series/21324-page-object-model-guide) design pattern which recommended in order to make tests easy to maintain.

**TODO:**
- Import the project as Maven project to Eclipse or Intellij. 
- Add your Perfecto Lab user, pass and host to [Utils.java](src/test/java/Utils.java) file.
- Add your Reddit Username and password to the [Reddit.java](src/test/java/Reddit.java) file:
```Java
    static String AppUser = System.getProperty("np.AppUser", "My_user");
    static String AppPass = System.getProperty("np.AppPass", "My_pass");
```
- Upload the Reddit application to your Perfecto Lab repository.
- Run as TestNG test from testng.xml file.

### Adding your favorite devices
By editing testng.xml file you could choose your favorite devices, inside the test suite for example:
```xml
    <test name="Test Android">
        <parameter name="platformName" value="Android" />
        <parameter name="platformVersion" value="" />
        <parameter name="model" value="Galaxy S6" />
        <parameter name="browserName" value="mobileOS" />
        <parameter name="browserVersion" value="" />
        <parameter name="deviceName" value="" />
        <classes>
            <class name="Reddit" />
        </classes>
    </test>
```

Adding parallel execution: 
```xml
 <suite name="Suite" parallel="tests">
 ```
