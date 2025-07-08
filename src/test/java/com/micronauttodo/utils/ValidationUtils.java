package com.micronauttodo.utils;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static <T> boolean hasFieldNotBlankViolation(Set<ConstraintViolation<T>> violations,
                                       String propertyName) {
        return hasViolation(violations, propertyName, "must not be blank");
    }

    public static <T> boolean hasNotNullViolation(Set<ConstraintViolation<T>> violations,
                                                    String propertyName) {
        return hasViolation(violations, propertyName, "must not be null");
    }

    public static <T> boolean hasMalformedEmailViolation(Set<ConstraintViolation<T>> violations,
                                              String propertyName) {
        return hasViolation(violations, propertyName, "must be a well-formed email address");
    }

    public static <T> boolean hasViolation(Set<ConstraintViolation<T>> violations,
                                       String propertyName,
                                       String violationMessage) {
        return violations.stream()
                .filter(x -> x.getPropertyPath().toString().equals(propertyName))
                .map(ConstraintViolation::getMessage)
                .anyMatch(violationMessage::equals);
    }
}
