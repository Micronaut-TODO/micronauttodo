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

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.*;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ServerFilter;
import io.micronaut.http.filter.FilterPatternStyle;
import io.micronaut.http.server.util.HttpHostResolver;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.csrf.conf.CsrfFilterConfigurationProperties;
import io.micronaut.security.csrf.exceptions.InvalidCsrfTokenException;
import io.micronaut.security.csrf.resolver.CsrfTokenResolver;
import io.micronaut.security.csrf.validator.CsrfTokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Requires(property = CsrfFilterConfigurationProperties.PREFIX +".enabled", value = StringUtils.TRUE, defaultValue = StringUtils.TRUE)
@Requires(classes = HttpRequest.class)
@ServerFilter(patternStyle = FilterPatternStyle.REGEX,
        value = "${" + CsrfFilterConfigurationProperties.PREFIX + ".regex-pattern:^.*$}",
        methods = {HttpMethod.POST})
public class CsrfTokenFilter {
    private static final Logger LOG = LoggerFactory.getLogger(CsrfTokenFilter.class);
    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";
    private static final String SLASH = "/";

    private final HttpHostResolver httpHostResolver;
    private final List<CsrfTokenResolver<HttpRequest<?>>> csrfTokenResolvers;
    private final CsrfTokenValidator csrfTokenValidator;
    private final ExceptionHandler<InvalidCsrfTokenException, MutableHttpResponse<?>> invalidCsrfTokenExceptionHandler;

    public CsrfTokenFilter(HttpHostResolver httpHostResolver,
                           List<CsrfTokenResolver<HttpRequest<?>>> csrfTokenResolvers,
                           CsrfTokenValidator csrfTokenValidator,
                           ExceptionHandler<InvalidCsrfTokenException, MutableHttpResponse<?>> invalidCsrfTokenExceptionHandler) {
        this.httpHostResolver = httpHostResolver;
        this.csrfTokenResolvers = csrfTokenResolvers;
        this.csrfTokenValidator = csrfTokenValidator;
        this.invalidCsrfTokenExceptionHandler = invalidCsrfTokenExceptionHandler;
    }

    @ExecuteOn(TaskExecutors.BLOCKING)
    @RequestFilter
    @Nullable
    public HttpResponse<?> filterRequest(HttpRequest<?> request) {
        if (request.getMethod() == HttpMethod.POST) {
            if (!validateHeaders(request)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Request rejected by the {} because the header validation failed", this.getClass().getSimpleName());
                }
                return unauthorized(request);
            }
            if (!validateCsrfToken(request)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Request rejected by the {} because the CSRF Token validation failed", this.getClass().getSimpleName());
                }
                return unauthorized(request);
            }
        }
        return null;
    }

    @Nullable
    private boolean validateCsrfToken(HttpRequest<?> request) {
        String csrfToken = null;
        for (CsrfTokenResolver<HttpRequest<?>> tokenResolver : csrfTokenResolvers) {
            Optional<String> tokenOptional = tokenResolver.resolveToken(request);
            if (tokenOptional.isPresent()) {
                csrfToken = tokenOptional.get();
                break;
            }
        }
        if (csrfToken == null) {
            return false;
        }
        return csrfTokenValidator.validate(csrfToken);
    }

    private HttpResponse<?> unauthorized(HttpRequest<?> request) {
        return invalidCsrfTokenExceptionHandler.handle(request, new InvalidCsrfTokenException());
    }

    boolean validateHeaders(HttpRequest<?> request) {
        String host = httpHostResolver.resolve(request);
        List<String> validateLocations = List.of(host);
        return validateHeaders(validateLocations, request.getHeaders(), request.getMethod());
    }

    private boolean validateHeaders(@NonNull List<String> validLocations,
                                    @NonNull HttpHeaders headers,
                                    @NonNull HttpMethod method) {
        String origin = headers.get(HttpHeaders.ORIGIN);
        String referer = cleanupReferer(headers.get(HttpHeaders.REFERER));
        if (method == HttpMethod.POST) {
            return referer != null && origin != null && validLocations.contains(referer) && validLocations.contains(origin);
        }
        if (method == HttpMethod.GET) {
            return referer != null && validLocations.contains(referer);
        }
        return false;
    }

    private String cleanupReferer(String referer) {
        if (referer != null) {
            boolean http = false;
            boolean https = false;
            if (referer.startsWith(HTTP)) {
                http = true;
                referer = referer.replaceAll(HTTP, "");
            } else if (referer.startsWith(HTTPS)) {
                https = true;
                referer = referer.replaceAll(HTTPS, "");
            }
            int index = referer.indexOf(SLASH);
            if (index != -1) {
                referer = referer.substring(0, index);
            }
            if (https) {
                referer = HTTPS + referer;
            } else if (http) {
                referer = HTTP + referer;
            }
        }
        return referer;
    }
}
