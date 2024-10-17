package com.micronauttodo.views;

import com.micronauttodo.AssertionUtils;
import com.micronauttodo.EmptyLocalizedMessageSource;
import com.micronauttodo.MicronautIntegrationTest;
import com.micronauttodo.controllers.locale.LocaleForm;
import com.micronauttodo.domains.Todo;
import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.io.Writable;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.views.fields.FormGenerator;
import io.micronaut.views.jte.HtmlJteViewsRenderer;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@MicronautIntegrationTest
class TodoListRenderTest {
    private static final Logger LOG = LoggerFactory.getLogger(TodoListRenderTest.class);
    @Inject
    HtmlJteViewsRenderer<Map<String, Object>> viewsRenderer;

    @Inject
    FormGenerator formGenerator;

    @Test
    void testRenderTodoIndex() throws IOException {
        List<Todo> todos = List.of(new Todo(12L, "Learn about GraalVM"));
        String viewName = "todo/list";
        LocalizedMessageSource messageSource = new EmptyLocalizedMessageSource();
        Writable writable = viewsRenderer.render(viewName, Map.of("todos", todos,
                "messageSource", messageSource,
                "localeForm", formGenerator.generate("/locale", LocaleForm.class)), null);
        String html = WritableUtils.writableToString(writable);
        AssertionUtils.assertHtmlContains(LOG, html, "<!doctype html>");
        AssertionUtils.assertHtmlContains(LOG, html, "Learn about GraalVM");
    }


}
