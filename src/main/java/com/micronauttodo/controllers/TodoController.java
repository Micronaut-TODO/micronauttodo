package com.micronauttodo.controllers;

import com.micronauttodo.controllers.api.v1.Api;
import com.micronauttodo.domains.Todo;
import com.micronauttodo.repositories.TodoRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.micronaut.views.turbo.TurboStream;
import io.micronaut.views.turbo.http.TurboMediaType;
import io.swagger.v3.oas.annotations.Hidden;

import java.net.URI;
import java.util.Map;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/todo")
class TodoController {
    public static final URI URI_TODO_LIST = UriBuilder.of("/todo").path("list").build();
    private static final String MODEL_TODOS = "todos";
    public static final String FIELD_ITEM = "item";
    public static final String FIELD_ID = "id";
    public static final String MODEL_TODO = "todo";
    public static final String VIEW_TODO_TODO_LIST_GROUP_ITEM = "todo/todoListGroupItem";
    private final TodoRepository todoRepository;

    TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Hidden
    @Produces(MediaType.TEXT_HTML)
    @Get(Api.PATH_LIST)
    @View("todo/list")
    Map<String, Object> list() {
        return Map.of(MODEL_TODOS, todoRepository.findAll());
    }

    @Hidden
    @Produces({MediaType.TEXT_HTML, TurboMediaType.TURBO_STREAM})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post(Api.PATH_SAVE)
    HttpResponse<?> save(@Body(FIELD_ITEM) String item, HttpRequest<?> request) {
        Todo todo = todoRepository.save(item);
        return TurboMediaType.acceptsTurboStream(request)
                ? HttpResponse.ok(TurboStream.builder()
                        .targetDomId("todoList")
                        .template(VIEW_TODO_TODO_LIST_GROUP_ITEM, Map.of(MODEL_TODO, todo))
                        .append()).contentType(TurboMediaType.TURBO_STREAM_TYPE)
                : HttpResponse.seeOther(URI_TODO_LIST);
    }

    @Hidden
    @Produces({MediaType.TEXT_HTML, TurboMediaType.TURBO_STREAM})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post(Api.PATH_DELETE)
    HttpResponse<?> save(@Body(FIELD_ID) Long id, HttpRequest<?> request) {
        todoRepository.deleteById(id);
        return TurboMediaType.acceptsTurboStream(request)
                ? HttpResponse.ok(TurboStream.builder()
                        .targetDomId("todo" + id)
                        .remove()).contentType(TurboMediaType.TURBO_STREAM_TYPE)
                : HttpResponse.seeOther(URI_TODO_LIST);
    }
}
