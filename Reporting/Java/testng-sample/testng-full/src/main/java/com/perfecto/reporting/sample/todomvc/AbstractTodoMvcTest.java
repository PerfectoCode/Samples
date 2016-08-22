package com.perfecto.reporting.sample.todomvc;

import com.perfecto.reporting.sample.AbstractPerfectoSeleniumTestNG;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Base class for TodoMvc sanity tests
 */
public abstract class AbstractTodoMvcTest extends AbstractPerfectoSeleniumTestNG {

    @BeforeClass
    public void beforeClass() {
        String url = getUrl();
        // Can't use testStep outside the context of a test. How do I denote setup steps?
        // reportiumClient.testStep("navigate to " + url);
        driver.get(url);
    }

    /**
     * Returns the By object that identifies the input box for adding new todos
     * @return returns the By object that identifies the input box for adding new todos
     */
    protected abstract By getNewTodoBy();

    /**
     * Returns the url for navigating to the TodoMVC app
     * @return returns the url for navigating to the TodoMVC app
     */
    protected abstract String getUrl();

    /**
     * Returns the name of the created todo
     * @param prefix Basis for the enw todo name. A unique postfix is appended to the prefix.
     * @return name of the created todo
     */
    protected String createUniqueTodo(String prefix) {
        String todoName = prefix + " " + UUID.randomUUID().toString();
        reportiumClient.testStep("Add new unique todo and commit with Enter key. Generated todo name is " + todoName);
        WebElement newTodo = findElement(getNewTodoBy());
        newTodo.sendKeys(todoName, Keys.ENTER);
        return todoName;
    }

    /**
     * Verifies that a single todo with the given name exists in the todos list
     * @param todoName Todo name
     */
    protected void verifyAddedTodo(String todoName) {
        reportiumClient.testStep("Validate new todo exists in todo list");
        List<WebElement> elements = driver.findElements(getTodoBy(todoName));
        Assert.assertEquals(elements.size(), 1);
    }

    /**
     * Removes the todo with the given name from the list. Assumes todo name is unique
     * @param todoName Todo name
     */
    protected void removeTodo(String todoName) {
        reportiumClient.testStep("Hover over new todo");
        List<WebElement> elements = driver.findElements(getTodoBy(todoName));
        Assert.assertEquals(elements.size(), 1);
        WebElement todo = elements.get(0);
        Actions builder = new Actions(driver);
        builder.moveToElement(todo).perform();

        reportiumClient.testStep("Remove todo by clicking on X");
        WebElement removeTodoX = todo.findElement(By.className("destroy"));
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(removeTodoX));
        // Clicking the WebElement does not work with Firefox
        builder.click(removeTodoX).perform();
    }

    /**
     * Verifies that the todo with the given name was removed from the list
     * @param todoName Todo name
     */
    protected void verifyRemovedTodo(String todoName) {
        reportiumClient.testStep("Validate new todo removed from list");
        new WebDriverWait(driver, 3)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .until(ExpectedConditions.invisibilityOfElementLocated(getTodoBy(todoName)));
    }

    /**
     * Mark todo with given name as completed
     */
    protected void completeTodo(String todoName) {
        WebElement todo = driver.findElement(getTodoBy(todoName));
        reportiumClient.testStep("Mark todo " + todoName + " as completed");
        WebElement checkbox = todo.findElement(By.tagName("input"));
        checkbox.click();
    }

    /**
     * Verify todo with given name is marked as completed
     */
    protected void verifyCompletedTodo(String todoName) {
        WebElement todo = driver.findElement(getTodoBy(todoName));
        reportiumClient.testStep("Mark todo " + todoName + " as completed");
        WebElement li = todo.findElement(By.xpath(".."));
        Assert.assertTrue(li.getAttribute("class").contains("completed"));
    }

    /**
     * Click on the "Completed" button to show only completed todos
     */
    protected void filterCompleted() {
        reportiumClient.testStep("Filter by completed");
        WebElement completed = findElement(By.xpath("//a[@href='#/completed']"));
        completed.click();
    }

    /**
     * Returns the By object for a given todo identified by its name
     * @param name todo name
     * @return By that identifies the todo by name
     */
    private By getTodoBy(String name) {
        return By.xpath("//div[./label[text()[normalize-space(.)='" + name + "']]]");
    }

}
