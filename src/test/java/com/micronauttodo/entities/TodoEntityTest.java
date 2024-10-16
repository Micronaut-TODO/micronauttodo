package com.micronauttodo.entities;

import com.micronauttodo.MicronautIntegrationTest;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautIntegrationTest
class TodoEntityTest {
    @Inject
    Validator validator;

    @Test
    void itemCannotBeBlank() {
        assertFalse(validator.validate(new TodoEntity(1L, "", null)).isEmpty());
        assertFalse(validator.validate(new TodoEntity(1L, null, null)).isEmpty());
    }

    @Test
    void idCannotBeNullableAsItIsGenerated() {
        assertTrue(validator.validate(new TodoEntity(null, "Learn about GraalVM", null)).isEmpty());
    }
}