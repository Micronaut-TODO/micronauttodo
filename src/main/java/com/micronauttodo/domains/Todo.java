package com.micronauttodo.domains;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Serdeable
public record Todo(@NonNull @NotNull Long id, @NonNull @NotBlank String item) {
}
