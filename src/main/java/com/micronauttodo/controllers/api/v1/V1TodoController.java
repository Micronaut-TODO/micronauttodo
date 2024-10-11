package com.micronauttodo.controllers.api.v1;

import com.micronauttodo.domains.Todo;
import com.micronauttodo.repositories.TodoRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller(V1TodoController.PATH_API_V1_TODO)
class V1TodoController {
    public static final String SLASH = "/";
    public static final String API = "api";
    public static final String V1 = "v1";
    public static final String TODO = "todo";
    public static final String PATH_API_V1_TODO = SLASH + API + SLASH + V1 + SLASH + TODO;
    private static Function<Long, URI> SHOW_URI = id -> UriBuilder.of(SLASH + API).path(V1).path(TODO).path("" + id).build();;

    private final TodoRepository todoRepository;

    V1TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Get
    HttpResponse<?> findAll() {
        List<Todo> todoList = todoRepository.findAll();
        return HttpResponse.ok(todoList);
    }

    @Post
    HttpResponse<?> save(@Body("item") String item) {
        Todo todo = todoRepository.save(item);
        return HttpResponse.created(SHOW_URI.apply(todo.id()));
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    void save(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }

    @Get("/{id}")
    Optional<Todo> show(@PathVariable Long id) {
        return todoRepository.findById(id);
    }
}
