package test;

import Objects.DriverProvider;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.steps.Steps;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;


public class PerfectoSteps extends Steps{

    protected WebDriver driver;

    public PerfectoSteps(){
        super();
    }

    public PerfectoSteps(DriverProvider provider){
        super();

        provider.initialize();
        driver = provider.get();
    }

//    @BeforeStories
//    public void BeforeStory() throws MalformedURLException {
//
//    }

    @AfterStories
    public void AfterStory(){
        driver.quit();
    }
}
