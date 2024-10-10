package com.micronauttodo.controllers;

import com.micronauttodo.controllers.api.v1.Api;
import com.micronauttodo.entities.TodoEntity;
import com.micronauttodo.mappers.TodoMappers;
import com.micronauttodo.repositories.TodoCrudRepository;
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
    private final TodoCrudRepository todoCrudRepository;
    private final TodoMappers todoMappers;

    TodoController(TodoCrudRepository todoCrudRepository,
                   TodoMappers todoMappers) {
        this.todoCrudRepository = todoCrudRepository;
        this.todoMappers = todoMappers;
    }

    @Hidden
    @Produces(MediaType.TEXT_HTML)
    @Get(Api.PATH_LIST)
    @View("todo/list")
    Map<String, Object> list() {
        return Map.of(MODEL_TODOS, todoCrudRepository.findAll().stream().map(todoMappers::fromEntity).toList());
    }

    @Hidden
    @Produces({MediaType.TEXT_HTML, TurboMediaType.TURBO_STREAM})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post(Api.PATH_SAVE)
    HttpResponse<?> save(@Body(FIELD_ITEM) String item, HttpRequest<?> request) {
        TodoEntity entity = todoCrudRepository.save(new TodoEntity(null, item, null));
        return TurboMediaType.acceptsTurboStream(request)
                ? HttpResponse.ok(TurboStream.builder().targetDomId("todoList").template(VIEW_TODO_TODO_LIST_GROUP_ITEM, Map.of(MODEL_TODO, todoMappers.fromEntity(entity))).append())
                : HttpResponse.seeOther(URI_TODO_LIST);
    }

    @Hidden
    @Produces({MediaType.TEXT_HTML, TurboMediaType.TURBO_STREAM})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Post(Api.PATH_DELETE)
    HttpResponse<?> save(@Body(FIELD_ID) Long id, HttpRequest<?> request) {
        todoCrudRepository.deleteById(id);
        return TurboMediaType.acceptsTurboStream(request)
                ? HttpResponse.ok(TurboStream.builder().targetDomId("todo" + id).remove())
                : HttpResponse.seeOther(URI_TODO_LIST);
    }
}
