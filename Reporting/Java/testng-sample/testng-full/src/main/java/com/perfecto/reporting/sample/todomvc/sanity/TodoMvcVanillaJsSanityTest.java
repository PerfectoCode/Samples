package com.perfecto.reporting.sample.todomvc.sanity;

import org.openqa.selenium.By;

/**
 * Sample test based on the canonical TodoMVC application, using the vanilla Javascript implementation of TodoMVC.
 *
 * @see  <a href="http://todomvc.com/examples/vanillajs/">http://todomvc.com/examples/vanillajs/</a>
 */
public class TodoMvcVanillaJsSanityTest extends AbstractTodoMvcSanityTest {

    protected By getNewTodoBy() {
        return By.className("new-todo");
    }

    protected String getUrl() {
        return "http://todomvc.com/examples/vanillajs/";
    }

}
