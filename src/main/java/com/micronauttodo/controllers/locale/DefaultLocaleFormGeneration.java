package com.micronauttodo.controllers.locale;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.server.util.locale.HttpLocaleResolver;
import io.micronaut.views.fields.Fieldset;
import io.micronaut.views.fields.FieldsetGenerator;
import io.micronaut.views.fields.Form;
import jakarta.inject.Singleton;

import java.util.Locale;

import static com.micronauttodo.controllers.locale.SupportedLocale.DEFAULT_LOCALE;
import static com.micronauttodo.controllers.locale.SupportedLocale.SUPPORTED_LOCALES;

@Singleton
class DefaultLocaleFormGeneration implements LocaleFormGeneration {
    private final HttpLocaleResolver httpLocaleResolver;
    private final FieldsetGenerator fieldsetGenerator;

    DefaultLocaleFormGeneration(HttpLocaleResolver httpLocaleResolver, FieldsetGenerator fieldsetGenerator) {
        this.httpLocaleResolver = httpLocaleResolver;
        this.fieldsetGenerator = fieldsetGenerator;
    }

    @Override
    public Form generateForm(HttpRequest<?> request) {
        Locale locale = httpLocaleResolver.resolveOrDefault(request);
        String lang = locale.getLanguage();
        if (lang == null || !SUPPORTED_LOCALES.containsKey(lang)) {
            lang = DEFAULT_LOCALE;
        }
        Fieldset fieldset = fieldsetGenerator.generate(new LocaleForm(lang));
        return new Form(LocaleController.PREFIX, "post", fieldset, MediaType.APPLICATION_FORM_URLENCODED, false);
    }
}
