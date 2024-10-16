package com.micronauttodo.controllers.locale;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.views.fields.elements.Option;
import io.micronaut.views.fields.fetchers.OptionFetcher;
import io.micronaut.views.fields.messages.Message;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class SupportedLocale implements OptionFetcher<String> {
    private static final String DEFAULT_MESSAGE_ES = "Español";
    private static final String DEFAULT_MESSAGE_EN = "English";
    private static final String EN = "en";
    public static final String DEFAULT_LOCALE = EN;
    private static final String ES = "es";
    public static final Map<String, String> SUPPORTED_LOCALES = Map.of(EN, DEFAULT_MESSAGE_EN, ES, DEFAULT_MESSAGE_ES);
    private static final List<Option> OPTIONS = SUPPORTED_LOCALES.keySet()
            .stream()
            .map(key -> Option.builder()
                    .value(key)
                    .label(Message.of(SUPPORTED_LOCALES.get(key))).build())
            .toList();

    @Override
    public List<Option> generate(@NonNull Class<String> type) {
        return OPTIONS;
    }

    @Override
    public List<Option> generate(@NonNull String locale) {
        List<Option> options = new ArrayList<>();
        Option.Builder builder = Option.builder().value(EN).label(Message.of(DEFAULT_MESSAGE_EN));
        options.add(locale.equals(EN) ? builder.selected(true).build() : builder.build());
        builder = Option.builder().value(ES).label(Message.of(DEFAULT_MESSAGE_ES));
        options.add(locale.equals(ES) ? builder.selected(true).build() : builder.build());
        return options;
    }
}
