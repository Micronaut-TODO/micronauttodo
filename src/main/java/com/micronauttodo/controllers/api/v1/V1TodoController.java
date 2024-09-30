package com.micronauttodo.controllers.api.v1;

import com.micronauttodo.domains.Todo;
import com.micronauttodo.entities.TodoEntity;
import com.micronauttodo.mappers.TodoMappers;
import com.micronauttodo.repositories.TodoCrudRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller(V1TodoController.PATH_API_V1_TODO)
class V1TodoController {
    public static final String SLASH = "/";
    public static final String API = "api";
    public static final String V1 = "v1";
    public static final String TODO = "todo";
    public static final String PATH_API_V1_TODO = SLASH + API + SLASH + V1 + SLASH + TODO;

    private final TodoCrudRepository todoCrudRepository;
    private final TodoMappers todoMappers;

    V1TodoController(TodoCrudRepository todoCrudRepository,
                     TodoMappers todoMappers) {
        this.todoCrudRepository = todoCrudRepository;
        this.todoMappers = todoMappers;
    }

    @Get
    HttpResponse<?> findAll(Pageable pageable) {
        List<Todo> todoList = todoCrudRepository.findAll(pageable).getContent().stream().map(todoMappers::fromEntity).toList();
        return HttpResponse.ok(todoList);
    }

    @Post
    HttpResponse<?> save(@Body("item") String item) {
        TodoEntity todo = todoCrudRepository.save(new TodoEntity(null, item, null));
        return HttpResponse.created(uri(todo.id()));
    }

    @Put("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    void update(@PathVariable Long id, @Body("item") String item) {
        todoCrudRepository.update(new TodoEntity(id, item, null));
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    void save(@PathVariable Long id) {
        todoCrudRepository.deleteById(id);
    }

    @Get("/{id}")
    Optional<Todo> show(@PathVariable Long id) {
        return todoCrudRepository.findById(id).map(todoMappers::fromEntity);
    }

    private static URI uri(Long id) {
        return UriBuilder.of(SLASH + API).path(V1).path(TODO).path("" + id).build();
    }
}
