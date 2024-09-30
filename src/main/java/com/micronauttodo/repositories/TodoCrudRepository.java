package com.micronauttodo.repositories;

import com.micronauttodo.entities.TodoEntity;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface TodoCrudRepository extends PageableRepository<TodoEntity, Long> {
}
