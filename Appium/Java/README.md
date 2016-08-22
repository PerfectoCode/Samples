## Appium - Java Samples

This samples of Appium driver in Java examine the using of AndroidDriver / IOSDriver to execute Native application tests.
Most of the projects are using [Maven](http://search.maven.org/) or [Gradle](https://gradle.org/) in order to manage the project dependencies,<br/>
And [TestNG](http://testng.org/doc/index.html) as a testing framework.

###Fast Starting:

In order to run the project it's recommended to download it and import as Maven/Gradle project on Eclipse or Intellij IDE.

### Maven:
Adding a dependency done by editing the project's pom.xml file.
For example adding Appium dependency: 

```xml
<dependencies>
 ....
 <dependency>
    <groupId>io.appium</groupId>
    <artifactId>java-client</artifactId>
    <version>4.1.0</version>
  </dependency>
  
</dependencies>
```

### Gradle: 
Adding a dependency done by editing projects gradle.build .
```xml
compile 'com.comcast.magic-wand:appium:4.0.1'
```
### TestNG: 
Test managment framework, in this projects most of the devices capabilities passed by the testng.xml file.
The tests executed via testng.xml file also as TestNG test.
