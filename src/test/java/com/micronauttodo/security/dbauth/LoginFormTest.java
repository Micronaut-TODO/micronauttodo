package com.micronauttodo.security.dbauth;

import com.micronauttodo.MicronautIntegrationTest;
import com.micronauttodo.MicronautTestNoPersistence;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.SerdeIntrospections;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@MicronautIntegrationTest
class LoginFormTest {
    public static final String EMAIL = "manolo@example.com";

    @Test
    void usernameIsRequired(Validator validator) {
        assertTrue(!validator.validate(new LoginForm(null, "rawpassword")).isEmpty());
        Set<ConstraintViolation<LoginForm>> violations = validator.validate(new LoginForm("", "rawpassword"));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username") && v.getMessage().equals("must not be blank")));

        violations = validator.validate(new LoginForm("foo", "rawpassword"));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username") && v.getMessage().equals("must be a well-formed email address")));

        violations = validator.validate(new LoginForm("foo", "rawpassword"));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username") && v.getMessage().equals("must be a well-formed email address")));

        assertTrue(validator.validate(new LoginForm(EMAIL, "rawpassword")).isEmpty());
    }

    @Test
    void passwordIsRequired(Validator validator) {
        Set<ConstraintViolation<LoginForm>> violations = validator.validate(new LoginForm(EMAIL, null));
        assertFalse(violations.isEmpty());
        violations = validator.validate(new LoginForm(EMAIL, ""));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password") && v.getMessage().equals("must not be blank")));
    }

    @Test
    void loginFormIsAnnotatedWithSerdeableDeserializable(SerdeIntrospections serdeIntrospections) {
        assertDoesNotThrow(() -> serdeIntrospections.getDeserializableIntrospection(Argument.of(LoginForm.class)));
    }

    @Test
    void loginFormIsAnnotatedWithSerdeableSerializable(SerdeIntrospections serdeIntrospections) {
        assertDoesNotThrow(() -> serdeIntrospections.getSerializableIntrospection(Argument.of(LoginForm.class)));
    }
}