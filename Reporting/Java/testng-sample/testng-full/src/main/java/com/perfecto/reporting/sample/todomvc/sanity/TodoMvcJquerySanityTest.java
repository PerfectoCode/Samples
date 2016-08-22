package com.perfecto.reporting.sample.todomvc.sanity;

import org.openqa.selenium.By;

/**
 * Sample test based on the canonical TodoMVC application, using the vanilla Javascript implementation of TodoMVC.
 *
 * @see  <a href="http://todomvc.com/examples/jquery/">http://todomvc.com/examples/jquery/</a>
 */
public class TodoMvcJquerySanityTest extends AbstractTodoMvcSanityTest {

    protected By getNewTodoBy() {
        return By.id("new-todo");
    }

    protected String getUrl() {
        return "http://todomvc.com/examples/jquery/";
    }
}
