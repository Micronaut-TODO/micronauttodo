package com.micronauttodo.views;

import com.micronauttodo.AssertionUtils;
import com.micronauttodo.domains.Todo;
import io.micronaut.core.io.Writable;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.views.jte.HtmlJteViewsRenderer;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@MicronautTest(startApplication = false)
class TodoListRenderTest {
    private static final Logger LOG = LoggerFactory.getLogger(TodoListRenderTest.class);
    @Inject
    HtmlJteViewsRenderer<Map<String, Object>> viewsRenderer;

    @Test
    void testRenderTodoIndex() throws IOException {
        List<Todo> todos = List.of(new Todo(12L, "Learn about GraalVM"));
        String viewName = "todo/list";
        Writable writable = viewsRenderer.render(viewName, Map.of("todos", todos), null);
        String html = WritableUtils.writableToString(writable);
        AssertionUtils.assertHtmlContains(LOG, html, "<!DOCTYPE html>");
        AssertionUtils.assertHtmlContains(LOG, html, "Learn about GraalVM");
    }


}
