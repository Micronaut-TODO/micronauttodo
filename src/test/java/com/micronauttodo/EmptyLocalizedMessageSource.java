package com.micronauttodo;

import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.core.annotation.NonNull;

import java.util.Map;
import java.util.Optional;

public class EmptyLocalizedMessageSource implements LocalizedMessageSource {
    @Override
    public @NonNull Optional<String> getMessage(@NonNull String code) {
        return Optional.empty();
    }

    @Override
    public @NonNull Optional<String> getMessage(@NonNull String code, Object... variables) {
        return Optional.empty();
    }

    @Override
    public @NonNull Optional<String> getMessage(@NonNull String code, Map<String, Object> variables) {
        return Optional.empty();
    }
}
