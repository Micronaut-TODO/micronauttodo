package com.micronauttodo.security.csrf;

import io.micronaut.context.annotation.Primary;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.csrf.validator.CsrfTokenValidator;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
@Primary
public class CompositeCsrfTokenValidator implements CsrfTokenValidator<HttpRequest<?>> {
    private final List<CsrfTokenValidator<HttpRequest<?>>> validators;

    public CompositeCsrfTokenValidator(List<CsrfTokenValidator<HttpRequest<?>>> validators) {
        this.validators = validators;
    }

    @Override
    public boolean validateCsrfToken(@NonNull HttpRequest<?> request, @NonNull String token) {
        return validators.stream().anyMatch(validator -> validator.validateCsrfToken(request, token));
    }
}
