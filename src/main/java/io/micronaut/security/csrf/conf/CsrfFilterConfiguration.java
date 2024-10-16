package io.micronaut.security.csrf.conf;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.util.Toggleable;

public interface CsrfFilterConfiguration extends Toggleable {

    @NonNull
    String getRegexPattern();
}
