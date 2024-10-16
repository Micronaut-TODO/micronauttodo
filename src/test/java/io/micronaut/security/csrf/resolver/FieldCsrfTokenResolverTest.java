package io.micronaut.security.csrf.resolver;

import io.micronaut.core.convert.ConversionService;
import io.micronaut.core.type.Argument;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(startApplication = false)
class FieldCsrfTokenResolverTest {

    @Inject
    ConversionService conversionService;
    @Test
    void conversionService() {

        Optional<Map<String, String>> convert = conversionService.convert("username=sidthesloth&csrfToken=abcde&password=slothsecret", Argument.mapOf(String.class, String.class));
        assertTrue(convert.isPresent());

    }

}