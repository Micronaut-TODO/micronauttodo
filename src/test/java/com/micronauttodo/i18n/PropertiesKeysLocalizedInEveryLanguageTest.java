package com.micronauttodo.i18n;

import io.micronaut.core.io.ResourceLoader;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@MicronautTest(startApplication = false)
class PropertiesKeysLocalizedInEveryLanguageTest {
    @Test
    void keysAreLocalized(ResourceLoader resourceLoader) throws IOException {
        List<String> suffixes = List.of("es");
        Optional<InputStream> messagesInputStreamOptional = resourceLoader.getResourceAsStream("classpath:i18n/messages.properties");
        assertTrue(messagesInputStreamOptional.isPresent());
        try (InputStream inputStream = messagesInputStreamOptional.get()) {
            Properties messages = new Properties();
            messages.load(inputStream);
            Set<String> keys = messages.keySet().stream().map(Object::toString).collect(Collectors.toSet());
            assertFalse(keys.isEmpty());
            for (String suffix : suffixes) {
                Optional<InputStream> messagesLocalizedInputStreamOptional = resourceLoader.getResourceAsStream("classpath:i18n/messages_" + suffix +  ".properties");
                assertTrue(messagesLocalizedInputStreamOptional.isPresent());
                try (InputStream messagesLocalizedInputStream = messagesLocalizedInputStreamOptional.get()) {
                    Properties messagesLocalized = new Properties();
                    messagesLocalized.load(messagesLocalizedInputStream);
                    Set<String> keysLocalized = messages.keySet().stream().map(Object::toString).collect(Collectors.toSet());
                    assertEquals(keys, keysLocalized);
                }
            }
        }
    }
}