package com.micronauttodo.repositories;

import com.micronauttodo.domains.Todo;
import com.micronauttodo.entities.TodoEntity;
import com.micronauttodo.mappers.TodoMappers;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
class TodoRepositoryImpl implements TodoRepository {
    private final TodoCrudRepository todoCrudRepository;
    private final TodoMappers todoMappers;

    TodoRepositoryImpl(TodoCrudRepository todoCrudRepository,
                       TodoMappers todoMappers) {
        this.todoCrudRepository = todoCrudRepository;
        this.todoMappers = todoMappers;
    }

    @Override
    public Todo save(@NotBlank String item) {
        return todoMappers.fromEntity(todoCrudRepository.save(new TodoEntity(null, item, null)));
    }

    @Override
    public void deleteById(@NotNull Long id) {
        todoCrudRepository.deleteById(id);    }

    @Override
    @NonNull
    public List<Todo> findAll() {
        return todoCrudRepository.findAll().stream().map(todoMappers::fromEntity).toList();
    }

    @Override
    @NonNull
    public Optional<Todo> findById(Long id) {
        return todoCrudRepository.findById(id).map(todoMappers::fromEntity);
    }
}
