package com.perfecto.reporting.sample.todomvc.sanity;

import com.perfecto.reporting.sample.AbstractPerfectoSeleniumTestJunit;
import com.perfecto.reporting.sample.todomvc.TodoMvcService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Sample test based on the canonical TodoMVC application, using the vanilla Javascript implementation of TodoMVC.
 *
 * @see  <a href="http://todomvc.com/examples/jquery/">http://todomvc.com/examples/jquery/</a>
 */
public class TodoMvcJquerySanityTest extends AbstractPerfectoSeleniumTestJunit {

    private static TodoMvcService todoMvcService;

    @BeforeClass
    public static void navigateToUrl() {
        String url = "http://todomvc.com/examples/jquery/";
        // Can't use testStep outside the context of a test. How do I denote setup steps?
        // reportiumClient.testStep("navigate to " + url);
        driver.get(url);

        todoMvcService = new TodoMvcService(driver, reportiumClient);
    }

    @Test
    public void addTodo() {
        String todoName = todoMvcService.createUniqueTodo("A shiny new todo", getNewTodoBy());
        todoMvcService.verifyAddedTodo(todoName);
    }

    @Test
    public void clearTodo() {
        String todoName = todoMvcService.createUniqueTodo("clear me", getNewTodoBy());
        todoMvcService.removeTodo(todoName);
        todoMvcService.verifyRemovedTodo(todoName);
    }

    protected By getNewTodoBy() {
        return By.id("new-todo");
    }

}
