package com.micronauttodo.views;

import io.micronaut.context.LocalizedMessageSource;
import io.micronaut.http.HttpRequest;
import jakarta.inject.Singleton;
import java.util.Map;

@Singleton
public class MessageSourceViewModelProcessor extends MapViewModelProcessor {

    private final LocalizedMessageSource messageSource;

    public MessageSourceViewModelProcessor(LocalizedMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected void populateModel(HttpRequest<?> request, Map<String, Object> viewModel) {
        viewModel.putIfAbsent("messageSource", messageSource);
    }
}
