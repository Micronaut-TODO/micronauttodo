package com.micronauttodo;

import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class AssertionUtils {
    private AssertionUtils() {

    }

    public static void assertHtmlContains(Logger log, String html, String expected) {
        boolean contains = html.contains(expected);
        if (!contains) {
            log.trace("{}", html);
        }
        assertTrue(contains);
    }

    public static void assertHtmlDoesNotContain(Logger log, String html, String expected) {
        boolean contains = html.contains(expected);
        if (contains) {
            log.trace("{}", html);
        }
        assertFalse(contains);
    }
}
