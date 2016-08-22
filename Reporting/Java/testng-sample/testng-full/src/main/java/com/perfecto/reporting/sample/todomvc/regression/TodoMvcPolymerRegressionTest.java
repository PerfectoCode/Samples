package com.perfecto.reporting.sample.todomvc.regression;

import com.perfecto.reporting.sample.todomvc.AbstractTodoMvcTest;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * Sample test based on the canonical TodoMVC application, using the vanilla Javascript implementation of TodoMVC.
 *
 * @see  <a href="http://todomvc.com/examples/polymer/">http://todomvc.com/examples/polymer/</a>
 */
public class TodoMvcPolymerRegressionTest extends AbstractTodoMvcTest {

    protected By getNewTodoBy() {
        return By.id("new-todo");
    }

    protected String getUrl() {
        return "http://todomvc.com/examples/polymer/";
    }

    @Test
    public void todoCrud() {
        String todoName = createUniqueTodo("polymer");

        completeTodo(todoName);

        verifyCompletedTodo(todoName);

        filterCompleted();

        // Check that the todo is filtered correctly
        verifyAddedTodo(todoName);

        removeTodo(todoName);
    }
}
