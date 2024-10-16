package com.micronauttodo.domains;

import com.micronauttodo.MicronautIntegrationTest;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautIntegrationTest
class TodoTest {
    @Inject
    Validator validator;

    @Test
    void itemCannotBeBlank() {
        assertFalse(validator.validate(new Todo(1L, "")).isEmpty());
        assertFalse(validator.validate(new Todo(1L, null)).isEmpty());
    }

    @Test
    void idCannotBeNull() {
        assertFalse(validator.validate(new Todo(null, "Learn about GraalVM")).isEmpty());
    }
}