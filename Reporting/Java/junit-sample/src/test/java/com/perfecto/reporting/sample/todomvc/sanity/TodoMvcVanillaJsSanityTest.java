package com.perfecto.reporting.sample.todomvc.sanity;

import com.perfecto.reporting.sample.AbstractPerfectoSeleniumTestJunit;
import com.perfecto.reporting.sample.todomvc.TodoMvcService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

/**
 * Sample test based on the canonical TodoMVC application, using the vanilla Javascript implementation of TodoMVC.
 *
 * @see  <a href="http://todomvc.com/examples/vanillajs/">http://todomvc.com/examples/vanillajs/</a>
 */
public class TodoMvcVanillaJsSanityTest extends AbstractPerfectoSeleniumTestJunit {

    private static TodoMvcService todoMvcService;

    @BeforeClass
    public static void navigateToUrl() {
        String url = "http://todomvc.com/examples/vanillajs/";
        // Can't use testStep outside the context of a test. How do I denote setup steps?
        // reportiumClient.testStep("navigate to " + url);
        driver.get(url);

        todoMvcService = new TodoMvcService(driver, reportiumClient);
    }

    @Test
    public void todoCrud() {
        String todoName = todoMvcService.createUniqueTodo("vanillajs", getNewTodoBy());

        todoMvcService.completeTodo(todoName);

        todoMvcService.verifyCompletedTodo(todoName);

        todoMvcService.filterCompleted();

        // Check that the todo is filtered correctly
        todoMvcService.verifyAddedTodo(todoName);

        todoMvcService.removeTodo(todoName);
    }

    protected By getNewTodoBy() {
        return By.className("new-todo");
    }
}
