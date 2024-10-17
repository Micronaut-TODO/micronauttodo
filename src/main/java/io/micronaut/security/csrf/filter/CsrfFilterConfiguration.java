package io.micronaut.security.csrf.filter;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.util.Toggleable;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.MediaType;

import java.util.Set;

public interface CsrfFilterConfiguration extends Toggleable {

    @NonNull
    String getRegexPattern();

    @NonNull
    Set<HttpMethod> getMethods();

    @NonNull
    Set<MediaType> getContentTypes();
}
