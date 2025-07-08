package com.micronauttodo.security;

import com.micronauttodo.MicronautIntegrationTest;
import io.micronaut.security.csrf.filter.CsrfFilterConfiguration;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import io.micronaut.core.util.PathMatcher;
import static org.junit.jupiter.api.Assertions.assertFalse;

@MicronautIntegrationTest
class CsrfRegexPatternTest {
    @Inject
    CsrfFilterConfiguration csrfFilterConfiguration;

    @Test
    void csrfRegexPattern() {
        assertFalse(PathMatcher.REGEX.matches(csrfFilterConfiguration.getRegexPattern(), "/logout"));
        assertFalse(PathMatcher.REGEX.matches(csrfFilterConfiguration.getRegexPattern(), "/login"));
        assertFalse(PathMatcher.REGEX.matches(csrfFilterConfiguration.getRegexPattern(), "/locale"));
        assertFalse(PathMatcher.REGEX.matches(csrfFilterConfiguration.getRegexPattern(), "/security/signup"));
        assertFalse(PathMatcher.REGEX.matches(csrfFilterConfiguration.getRegexPattern(), "/api/v1/todo"));

    }
}
