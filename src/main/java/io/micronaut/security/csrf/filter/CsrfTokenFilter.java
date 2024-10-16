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
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.*;
import io.micronaut.http.annotation.RequestFilter;
import io.micronaut.http.annotation.ServerFilter;
import io.micronaut.http.filter.FilterPatternStyle;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthorizationException;
import io.micronaut.security.csrf.resolver.CsrfTokenResolver;
import io.micronaut.security.csrf.validator.CsrfTokenValidator;
import io.micronaut.security.filters.SecurityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Internal
@Requires(property = CsrfFilterConfigurationProperties.PREFIX +".enabled", value = StringUtils.TRUE, defaultValue = StringUtils.TRUE)
@Requires(classes = HttpRequest.class)
@ServerFilter(patternStyle = FilterPatternStyle.REGEX, value = "${" + CsrfFilterConfigurationProperties.PREFIX + ".regex-pattern:^.*$}")
final class CsrfTokenFilter {
    private static final Logger LOG = LoggerFactory.getLogger(CsrfTokenFilter.class);
    private final List<CsrfTokenResolver<HttpRequest<?>>> csrfTokenResolvers;
    private final CsrfTokenValidator csrfTokenValidator;
    private final ExceptionHandler<AuthorizationException, MutableHttpResponse<?>> exceptionHandler;
    private final CsrfFilterConfiguration csrfFilterConfiguration;

    CsrfTokenFilter(CsrfFilterConfiguration csrfFilterConfiguration,
                           List<CsrfTokenResolver<HttpRequest<?>>> csrfTokenResolvers,
                           CsrfTokenValidator csrfTokenValidator,
                           ExceptionHandler<AuthorizationException, MutableHttpResponse<?>> exceptionHandler) {
        this.csrfTokenResolvers = csrfTokenResolvers;
        this.csrfTokenValidator = csrfTokenValidator;
        this.exceptionHandler = exceptionHandler;
        this.csrfFilterConfiguration = csrfFilterConfiguration;
    }

    @ExecuteOn(TaskExecutors.BLOCKING)
    @RequestFilter
    @Nullable
    public HttpResponse<?> csrfFilter(@NonNull HttpRequest<?> request) {
        if (!shouldTheFilterProcessTheRequestAccordingToTheHttpMethod(request)) {
            return null;
        }
        if (!shouldTheFilterProcessTheRequestAccordingToTheContentType(request)) {
            return null;
        }
        if (!validateCsrfToken(request)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Request rejected by the {} because the CSRF Token validation failed", this.getClass().getSimpleName());
            }
            return unauthorized(request);
        }
        return null;
    }

    private boolean shouldTheFilterProcessTheRequestAccordingToTheContentType(@NonNull HttpRequest<?> request) {
        if (request.getContentType().isPresent() && csrfFilterConfiguration.getContentTypes().stream().noneMatch(method -> method.equals(request.getContentType().get()))) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Request {} {} with content type {} is not processed by the CSRF filter. CSRF filter only processes Content Types: {}",
                        request.getMethod(),
                        request.getPath(),
                        request.getContentType().get(),
                        csrfFilterConfiguration.getContentTypes().stream().map(MediaType::toString).toList());
            }
            return false;
        }
        return true;
    }

    private boolean shouldTheFilterProcessTheRequestAccordingToTheHttpMethod(@NonNull HttpRequest<?> request) {
        if (csrfFilterConfiguration.getMethods().stream().noneMatch(method -> method.equals(request.getMethod()))) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Request {} {} not processed by the CSRF filter. CSRF filter only processes HTTP Methods: {}",
                        request.getMethod(),
                        request.getPath(),
                        csrfFilterConfiguration.getMethods().stream().map(HttpMethod::name).toList());
            }
            return false;
        }
        return true;
    }

    @Nullable
    private String resolveCsrfToken(@NonNull HttpRequest<?> request) {
        String csrfToken = null;
        for (CsrfTokenResolver<HttpRequest<?>> tokenResolver : csrfTokenResolvers) {
            Optional<String> tokenOptional = tokenResolver.resolveToken(request);
            if (tokenOptional.isPresent()) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("CSRF token resolved via {}", tokenResolver.getClass().getSimpleName());
                }
                csrfToken = tokenOptional.get();
                break;
            }
        }
        return csrfToken;
    }

    private boolean validateCsrfToken(@NonNull HttpRequest<?> request) {
        String csrfToken = resolveCsrfToken(request);
        if (csrfToken == null) {
            LOG.trace("No CSRF token found in request");
            return false;
        }
        return csrfTokenValidator.validateCsrfToken(request, csrfToken);
    }

    @NonNull
    private HttpResponse<?> unauthorized(@NonNull HttpRequest<?> request) {
        Authentication authentication = request.getAttribute(SecurityFilter.AUTHENTICATION, Authentication.class)
                .orElse(null);
        return exceptionHandler.handle(request,
                new AuthorizationException(authentication));
    }
}
