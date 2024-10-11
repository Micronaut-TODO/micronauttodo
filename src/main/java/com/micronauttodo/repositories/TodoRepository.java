package com.micronauttodo.repositories;

import com.micronauttodo.domains.Todo;
import io.micronaut.core.annotation.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    @NonNull
    Todo save(@NotBlank String item);

    void deleteById(@NotNull Long id);

    @NonNull
    List<Todo> findAll();

    @NonNull
    Optional<Todo> findById(@NotNull Long id);
}
