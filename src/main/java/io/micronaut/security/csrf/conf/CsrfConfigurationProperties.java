package io.micronaut.security.csrf.conf;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.config.SecurityConfigurationProperties;

@ConfigurationProperties(CsrfConfigurationProperties.PREFIX)
public class CsrfConfigurationProperties implements CsrfConfiguration {
    public static final String PREFIX = SecurityConfigurationProperties.PREFIX + ".csrf";

    public static final String DEFAULT_HTTP_HEADER_NAME = "X-CSRF-TOKEN";

    public static final String DEFAULT_FIELD_NAME = "csrfToken";

    private String headerName = DEFAULT_HTTP_HEADER_NAME;

    private String fieldName = DEFAULT_FIELD_NAME;

    @Override
    @NonNull
    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(@NonNull String headerName) {
        this.headerName = headerName;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
