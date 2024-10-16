package com.micronauttodo.views;

import com.micronauttodo.controllers.locale.LocaleFormGeneration;
import io.micronaut.http.HttpRequest;
import io.micronaut.views.fields.Form;
import jakarta.inject.Singleton;

import java.util.Map;

@Singleton
public class LocaleFormViewModelProcessor extends MapViewModelProcessor {
    private final LocaleFormGeneration localeFormGeneration;
    public static final String MODEL_LOCALE_FORM = "localeForm";

    public LocaleFormViewModelProcessor(LocaleFormGeneration localeFormGeneration) {
        this.localeFormGeneration = localeFormGeneration;
    }

    @Override
    protected void populateModel(HttpRequest<?> request, Map<String, Object> viewModel) {
        Form localeForm = localeFormGeneration.generateForm(request);
        viewModel.putIfAbsent(MODEL_LOCALE_FORM, localeForm);

    }
}
