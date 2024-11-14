package com.micronauttodo.entities;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.*;
import io.micronaut.security.annotation.CreatedBy;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@MappedEntity("csrftoken")
public record CsrfTokenEntity(@Id @GeneratedValue @Nullable Long id,
                         @NotBlank @NonNull String token,
                         @Nullable @DateCreated LocalDateTime dateCreated) {
}
