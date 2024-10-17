/*
 * Copyright 2017-2024 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.security.csrf.filter;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.MediaType;
import io.micronaut.security.csrf.conf.CsrfConfigurationProperties;

import java.util.Set;

@Internal
@ConfigurationProperties(CsrfFilterConfigurationProperties.PREFIX)
final class CsrfFilterConfigurationProperties implements CsrfFilterConfiguration {
    public static final String PREFIX = CsrfConfigurationProperties.PREFIX + ".filter";
    public static final boolean DEFAULT_BOOLEAN = true;
    private boolean enabled = DEFAULT_BOOLEAN;
    private String regexPattern = "^.*$";

    private static final Set<HttpMethod> DEFAULT_METHODS = Set.of(
            HttpMethod.POST,
            HttpMethod.PUT,
            HttpMethod.DELETE,
            HttpMethod.PATCH
    );
    private Set<HttpMethod> methods = DEFAULT_METHODS;

    private static final Set<MediaType> DEFAULT_CONTENT_TYPES = Set.of(
            MediaType.APPLICATION_FORM_URLENCODED_TYPE,
            MediaType.MULTIPART_FORM_DATA_TYPE
    );
    private Set<MediaType> contentTypes = DEFAULT_CONTENT_TYPES;

    @Override
    @NonNull
    public Set<HttpMethod> getMethods() {
        return methods;
    }

    public void setMethods(@NonNull Set<HttpMethod> methods) {
        this.methods = methods;
    }

    @Override
    @NonNull
    public Set<MediaType> getContentTypes() {
        return contentTypes;
    }

    public void setContentTypes(@NonNull Set<MediaType> contentTypes) {
        this.contentTypes = contentTypes;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getRegexPattern() {
        return regexPattern;
    }

    public void setRegexPattern(String regexPattern) {
        this.regexPattern = regexPattern;
    }
}
