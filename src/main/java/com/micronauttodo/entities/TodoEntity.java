package com.micronauttodo.entities;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.TenantId;
import io.micronaut.security.annotation.CreatedBy;
import jakarta.validation.constraints.NotBlank;

@MappedEntity("todo")
public record TodoEntity(@Id @GeneratedValue @Nullable Long id,
                         @NotBlank @NonNull String item,
                         @Nullable @TenantId @CreatedBy String username) {
}
