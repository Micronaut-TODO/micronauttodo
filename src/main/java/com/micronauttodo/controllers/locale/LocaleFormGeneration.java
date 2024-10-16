package com.micronauttodo.controllers.locale;

import io.micronaut.context.annotation.DefaultImplementation;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.views.fields.Form;
import jakarta.validation.constraints.NotNull;

@DefaultImplementation(DefaultLocaleFormGeneration.class)
@FunctionalInterface
public interface LocaleFormGeneration {

    @NonNull
    Form generateForm(@NonNull @NotNull HttpRequest<?> request);
}
