package com.micronauttodo.security;

import io.micronaut.security.csrf.conf.CsrfFilterConfiguration;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import io.micronaut.core.util.PathMatcher;
import static org.junit.jupiter.api.Assertions.assertFalse;

@MicronautTest(startApplication = false)
class CsrfRegexPatternTest {
    @Inject
    CsrfFilterConfiguration csrfFilterConfiguration;

    @Test
    void csrfRegexPattern() {
        assertFalse(PathMatcher.REGEX.matches(csrfFilterConfiguration.getRegexPattern(), "/locale"));
        assertFalse(PathMatcher.REGEX.matches(csrfFilterConfiguration.getRegexPattern(), "/security/login"));
        assertFalse(PathMatcher.REGEX.matches(csrfFilterConfiguration.getRegexPattern(), "/security/signup"));
        assertFalse(PathMatcher.REGEX.matches(csrfFilterConfiguration.getRegexPattern(), "/assets/stylesheets/bootstrap-5.3.3-dist/bootstrap.css"));
        assertFalse(PathMatcher.REGEX.matches(csrfFilterConfiguration.getRegexPattern(), "/api/v1/todo"));

    }
}
