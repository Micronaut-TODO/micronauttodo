package com.micronauttodo.security.dbauth;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface UserCrudRepository extends CrudRepository<@Valid UserEntity, Long> {
    Optional<UserEntity> findByUsername(@NotBlank String username);
}
