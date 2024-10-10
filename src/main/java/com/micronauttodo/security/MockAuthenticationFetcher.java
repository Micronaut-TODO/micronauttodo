package com.micronauttodo.security;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.AuthenticationFetcher;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

@Requires(env = Environment.DEVELOPMENT)
@Singleton
class MockAuthenticationFetcher implements AuthenticationFetcher<HttpRequest<?>> {
    @Override
    public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
        return Publishers.just(Authentication.build("sdelamo"));
    }
}
