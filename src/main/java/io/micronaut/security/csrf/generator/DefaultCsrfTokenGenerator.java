package io.micronaut.security.csrf.generator;

import jakarta.inject.Singleton;

import java.util.UUID;

@Singleton
public class DefaultCsrfTokenGenerator implements CsrfTokenGenerator {

    @Override
    public String generate(Object request) {
        return UUID.randomUUID().toString();
    }
}
