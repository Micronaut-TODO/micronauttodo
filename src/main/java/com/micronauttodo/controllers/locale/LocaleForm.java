package com.micronauttodo.controllers.locale;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.views.fields.annotations.Select;

@Introspected
public record LocaleForm(
    @Select(fetcher = SupportedLocale.class) String locale
) {
}
