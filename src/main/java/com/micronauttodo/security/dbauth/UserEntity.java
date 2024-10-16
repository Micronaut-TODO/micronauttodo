package com.micronauttodo.security.dbauth;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@MappedEntity("appuser")
public record UserEntity(
        @Id
        @GeneratedValue
        @Nullable
        Long id,

        @Nullable
        @NotBlank
        @Email
        String username,

        @NotBlank
        String password
) {
}
