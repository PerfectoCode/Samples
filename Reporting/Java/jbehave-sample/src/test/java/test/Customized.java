package test;

import Objects.DriverProvider;
import org.jbehave.core.annotations.*;
import org.junit.Assert;
import org.openqa.selenium.By;

public class Customized extends PerfectoSteps {

    public Customized(DriverProvider provider){
        super(provider);
    }

    @Given("I navigate to google")
    public void Step1() {
        driver.get("https://www.google.com");
    }

    @When("I search for PerfectoCode GitHub")
    public void Step2() throws Exception {
        driver.findElement(By.name("q")).sendKeys("PerfectoCode GitHub");
        driver.findElement(By.id("tsbb")).click();
    }

    @Then("I click the first search result")
    public void Step3() {
        driver.findElement(By.cssSelector("#rso > div > div:nth-child(1) > div > div > div._OXf > div._fSg > h3 > a")).click();
    }

    @Then("I validate Perfecto is in the page's title")
    public void Step4(){
        Assert.assertTrue(driver.getTitle().contains("Perfecto"));
    }

    @Then("I click imaginary button")
    public void Step5(){
        driver.findElement(By.cssSelector("#rso > div > div:nth-child(1) > div > div > div._OXf > div._fSg > h3 > abcdefg")).click();
    }

}