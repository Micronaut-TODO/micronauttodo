package io.micronaut.security.csrf.conf;

import com.micronauttodo.MicronautIntegrationTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautIntegrationTest
class CsrfConfigurationTest {

    @Inject
    CsrfConfiguration csrfConfiguration;

    @Test
    void defaultHeaderName() {
        assertEquals("X-CSRF-TOKEN", csrfConfiguration.getHeaderName());
    }

    @Test
    void defaultFieldName() {
        assertEquals("csrfToken", csrfConfiguration.getFieldName());
    }

    @Test
    void defaultEnabled() {
        assertTrue(csrfConfiguration.isEnabled());
    }
}