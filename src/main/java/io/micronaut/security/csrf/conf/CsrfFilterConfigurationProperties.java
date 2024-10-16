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
package io.micronaut.security.csrf.conf;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties(CsrfFilterConfigurationProperties.PREFIX)
public class CsrfFilterConfigurationProperties implements CsrfFilterConfiguration {
    public static final String PREFIX = CsrfConfigurationProperties.PREFIX + ".filter";
    public static final boolean DEFAULT_BOOLEAN = true;
    private boolean enabled = DEFAULT_BOOLEAN;
    private String regexPattern = "^.*$";

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
