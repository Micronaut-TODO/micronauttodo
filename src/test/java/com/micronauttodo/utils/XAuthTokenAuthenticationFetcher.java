package com.micronauttodo.utils;

import com.micronauttodo.MicronautIntegrationTest;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.AuthenticationFetcher;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

@Singleton
public class XAuthTokenAuthenticationFetcher  implements AuthenticationFetcher<HttpRequest<?>> {
    public static final String X_AUTH_TOKEN = "X-Auth-Token";

    @Override
    public Publisher<Authentication> fetchAuthentication(HttpRequest<?> request) {
        String authToken = request.getHeaders().get(X_AUTH_TOKEN);
        if (authToken == null) {
            return Publishers.empty();
        }
        return Publishers.just(Authentication.build(authToken));
    }
}
