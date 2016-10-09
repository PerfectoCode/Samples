package test;

import Objects.DriverProvider;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.steps.Steps;
import org.openqa.selenium.WebDriver;


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
        driver.close();
        driver.quit();
    }
}
