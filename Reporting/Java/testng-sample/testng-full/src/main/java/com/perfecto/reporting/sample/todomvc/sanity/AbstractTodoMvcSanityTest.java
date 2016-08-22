package com.perfecto.reporting.sample.todomvc.sanity;

import com.perfecto.reporting.sample.todomvc.AbstractTodoMvcTest;
import org.testng.annotations.Test;

/**
 * Base class for TodoMvc sanity tests
 */
public abstract class AbstractTodoMvcSanityTest extends AbstractTodoMvcTest {

    @Test
    public void addTodo() {
        String todoName = createUniqueTodo("A shiny new todo");
        verifyAddedTodo(todoName);
    }

    @Test
    public void clearTodo() {
        String todoName = createUniqueTodo("clear me");
        removeTodo(todoName);
        verifyRemovedTodo(todoName);
    }
}
