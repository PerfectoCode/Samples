package com.perfecto.reporting.sample.todomvc.sanity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Demonstrate Perfecto's reporting solution with Allure's @Step annotation
 */
public class TodoMvcBackboneSanity extends AbstractTodoMvcSanityTest {

    @Override
    protected By getNewTodoBy() {
        return By.className("new-todo");
    }

    @Override
    protected String getUrl() {
        return "http://todomvc.com/examples/backbone/";
    }

    @Test
    public void todoFiltering() {
        String todoName = createUniqueTodo("backbone");
        filterActive();

        reportiumClient.testStep("Verify todo " + todoName + " appears in the active todos list");
        verifyAddedTodo(todoName);

        reportiumClient.testStep("Mark todo " + todoName + " as completed - it should be removed from the list");
        completeTodo(todoName);

        verifyRemovedTodo(todoName);

        verifyTitle();
    }

    /**
     * Click on the "Completed" button to show only completed todos
     */
    @Step("Filter active todos")
    protected void filterActive() {
        WebElement active = findElement(By.xpath("//a[@href='#/active']"));
        active.click();
    }

    @Step("Check that title equals 'Backbone.js • TodoMVC'")
    protected void verifyTitle() {
        Assert.assertEquals(driver.getTitle(), "Backbone.js • TodoMVC");
    }
}
