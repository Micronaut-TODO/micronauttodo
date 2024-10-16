package com.micronauttodo.i18n;

import io.micronaut.context.MessageSource;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@MicronautTest(startApplication = false)
class MessageSourceFactoryTest {
    @Test
    void messageSource(MessageSource messageSource) {
        Optional<String> message = messageSource.getMessage("logout", Locale.ENGLISH);
        assertTrue(message.isPresent());
        assertEquals("Logout", message.get());

        message = messageSource.getMessage("logout", Locale.of("es"));
        assertTrue(message.isPresent());
        assertEquals("Salir", message.get());
    }
}