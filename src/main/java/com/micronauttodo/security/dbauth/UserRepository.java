package com.micronauttodo.security.dbauth;

import io.micronaut.core.annotation.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UserRepository {
    void save(@NonNull @NotNull @Valid SignUpForm signUpForm);
}
