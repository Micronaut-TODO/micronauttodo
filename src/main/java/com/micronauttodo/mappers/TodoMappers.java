package com.micronauttodo.mappers;

import com.micronauttodo.domains.Todo;
import com.micronauttodo.entities.TodoEntity;
import io.micronaut.context.annotation.Mapper;

public interface TodoMappers {
    @Mapper
    Todo fromEntity(TodoEntity entity);
}
