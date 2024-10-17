package com.micronauttodo.security.dbauth;

import io.micronaut.http.uri.UriBuilder;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationResponse;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoginFailureUtilsTest {
    @Test
    void testNoFailure() {
        String loginFailure = "/foo";
        Optional<URI> uriOptional = LoginFailureUtils.loginFailure(loginFailure, AuthenticationResponse.success("foo@example.com"));
        assertTrue(uriOptional.isPresent());
        assertEquals(URI.create(loginFailure), uriOptional.get());
    }

    @Test
    void testFailure() {
        String loginFailure = "/foo";
        AuthenticationFailureReason reason = AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH;
        Optional<URI> uriOptional = LoginFailureUtils.loginFailure(loginFailure, AuthenticationResponse.failure(reason));
        assertTrue(uriOptional.isPresent());
        assertEquals(UriBuilder.of(loginFailure).queryParam("reason", reason.toString()).build(), uriOptional.get());
    }
}