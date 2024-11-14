package com.micronauttodo.security.csrf;

import com.micronauttodo.entities.CsrfTokenEntity;
import com.micronauttodo.repositories.CsrfTokenEntityCrudRepository;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.order.Ordered;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.csrf.generator.CsrfTokenGenerator;
import io.micronaut.security.csrf.repository.CsrfTokenRepository;
import io.micronaut.security.filters.SecurityFilter;
import jakarta.inject.Singleton;

import java.net.Authenticator;
import java.util.Optional;

//@Singleton
class SessionLessCsrfRepository implements CsrfTokenRepository<HttpRequest<?>> {
    private final CsrfTokenEntityCrudRepository repository;
    private final CsrfTokenGenerator<HttpRequest<?>> csrfTokenGenerator;

    SessionLessCsrfRepository(CsrfTokenEntityCrudRepository repository, CsrfTokenGenerator<HttpRequest<?>> csrfTokenGenerator) {
        this.repository = repository;
        this.csrfTokenGenerator = csrfTokenGenerator;
    }

    @Override
    public @NonNull Optional<String> findCsrfToken(@NonNull HttpRequest<?> request) {
        if (request.getAttribute(SecurityFilter.AUTHENTICATION, Authenticator.class).isPresent()) {
            return Optional.empty();
        }
        String token = csrfTokenGenerator.generateCsrfToken(request);
        repository.save(new CsrfTokenEntity(null, token, null));
        return Optional.of(token);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
