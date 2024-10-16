package com.micronauttodo.security.dbauth;

import com.micronauttodo.MicronautIntegrationTest;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautIntegrationTest
class PasswordEncoderTest {
    @Test
    void passwordEncoder(PasswordEncoder passwordEncoder) {
        String rawPassword = "admin123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}