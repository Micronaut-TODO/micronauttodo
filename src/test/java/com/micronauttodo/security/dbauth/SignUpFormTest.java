package com.micronauttodo.security.dbauth;

import com.micronauttodo.MicronautIntegrationTest;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.SerdeIntrospections;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautIntegrationTest
class SignUpFormTest {
    @Test
    void emailCannotBeBlank(Validator validator) {
        assertFalse(validator.validate(new SignUpForm("", "secret", "secret")).isEmpty());
        assertFalse(validator.validate(new SignUpForm("", "secret", "secret")).isEmpty());
    }

    @Test
    void passwordAndRepeatPasswordCannotBeBlank(Validator validator) {
        assertFalse(validator.validate(new SignUpForm("sergio.delamo@softamo.com", "", "")).isEmpty());
        assertFalse(validator.validate(new SignUpForm("sergio.delamo@softamo.com", "", "")).isEmpty());
    }

    @Test
    void passwordAndRepeatPasswordShouldMatch(Validator validator) {
        assertFalse(validator.validate(new SignUpForm("sergio.delamo@softamo.com", "secret", "foobar")).isEmpty());
    }

    @Test
    void matchingPasswordsAndEmailisValid(Validator validator) {
        assertTrue(validator.validate(new SignUpForm("sergio.delamo@softamo.com", "secret", "secret")).isEmpty());
    }

    @Test
    void signupFormIsAnnotatedWithSerdeableDeserializable(SerdeIntrospections serdeIntrospections) {
        assertDoesNotThrow(() -> serdeIntrospections.getDeserializableIntrospection(Argument.of(SignUpForm.class)));
    }

    @Test
    void signupFormIsAnnotatedWithSerdeableSerializable(SerdeIntrospections serdeIntrospections) {
        assertDoesNotThrow(() -> serdeIntrospections.getSerializableIntrospection(Argument.of(SignUpForm.class)));
    }
}