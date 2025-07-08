package io.micronaut.bootstrap;

import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.SerdeIntrospections;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.validation.validator.Validator;
import io.micronaut.views.fields.messages.Message;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;

import static com.micronauttodo.utils.ValidationUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@Property(name = "spec.name", value = "AlertTest")
@MicronautTest(startApplication = false)
class AlertTest {
    @Test
    void messageCannotBeNull(Validator validator, AlertTestValidator testValidator) {
        assertTrue(hasNotNullViolation(validator.validate(Alert.danger(null)), "message"));

        String message = null;
        assertThrows(ConstraintViolationException.class, () ->testValidator.validate(new Alert(message, AlertVariant.DANGER, true)));
    }

    @Test
    void alertInfo() {
        assertEquals(AlertVariant.INFO, Alert.info(Message.of("Hello")).variant());
        assertFalse(Alert.info(Message.of("Hello"), false).dismissible());
    }

    @Test
    void alertDanger() {
        assertEquals(AlertVariant.DANGER, Alert.danger(Message.of("Hello")).variant());
        assertFalse(Alert.danger(Message.of("Hello"), false).dismissible());
    }

    @Test
    void alertIsAnnotatedWithSerdeableDeserializable(SerdeIntrospections serdeIntrospections) {
        assertDoesNotThrow(() -> serdeIntrospections.getDeserializableIntrospection(Argument.of(Alert.class)));
    }

    @Test
    void alertIsAnnotatedWithSerdeableSerializable(SerdeIntrospections serdeIntrospections) {
        assertDoesNotThrow(() -> serdeIntrospections.getSerializableIntrospection(Argument.of(Alert.class)));
    }

    @Requires(property = "spec.name", value = "AlertTest")
    @Singleton
    static class AlertTestValidator {
        void validate(@Valid Alert alert) {
            // ...
        }
    }
}