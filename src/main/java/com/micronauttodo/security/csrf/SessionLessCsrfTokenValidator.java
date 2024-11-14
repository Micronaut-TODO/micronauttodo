package com.micronauttodo.security.csrf;

import com.micronauttodo.repositories.CsrfTokenEntityCrudRepository;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.order.Ordered;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.csrf.validator.CsrfTokenValidator;
import io.micronaut.security.filters.SecurityFilter;
import jakarta.inject.Singleton;

import java.net.Authenticator;

@Singleton
class SessionLessCsrfTokenValidator implements CsrfTokenValidator<HttpRequest<?>> {
    private final CsrfTokenEntityCrudRepository csrfTokenEntityCrudRepository;

    SessionLessCsrfTokenValidator(CsrfTokenEntityCrudRepository csrfTokenEntityCrudRepository) {
        this.csrfTokenEntityCrudRepository = csrfTokenEntityCrudRepository;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public boolean validateCsrfToken(@NonNull HttpRequest<?> request, @NonNull String token) {
        if (request.getAttribute(SecurityFilter.AUTHENTICATION, Authenticator.class).isPresent()) {
            return false;
        }
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        return csrfTokenEntityCrudRepository.existsByToken(token);
    }
}
