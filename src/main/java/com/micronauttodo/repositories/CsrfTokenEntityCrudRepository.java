package com.micronauttodo.repositories;

import com.micronauttodo.entities.CsrfTokenEntity;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface CsrfTokenEntityCrudRepository extends CrudRepository<CsrfTokenEntity, Long> {
    boolean existsByToken(@NonNull String token);
}
