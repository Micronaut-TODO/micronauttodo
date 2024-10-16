package com.micronauttodo.controllers.locale;

import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.http.server.util.locale.HttpLocaleResolutionConfiguration;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Hidden;

import java.net.URI;
import java.util.function.BiFunction;

@Controller(LocaleController.PREFIX)
class LocaleController {
    public static final String PREFIX = "/locale";

    private final String cookieName;
    private final URI uri;
    private final BiFunction<String, String, Cookie> cookieBuilder = (name, value) -> Cookie.of(name, value)
            .maxAge(60 * 60 * 24 * 365);

    LocaleController(HttpLocaleResolutionConfiguration localeResolutionConfiguration) {
        this.cookieName = localeResolutionConfiguration.getCookieName().orElseThrow(() -> new ConfigurationException("Cookie name not configured"));
        this.uri = URI.create("/");
    }

    @Hidden
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Produces(MediaType.TEXT_HTML)
    @Post
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    HttpResponse<?> changeLocale(@Body("locale") String locale) {
        return HttpResponse.seeOther(uri).cookie(cookieBuilder.apply(cookieName, locale));
    }
}
