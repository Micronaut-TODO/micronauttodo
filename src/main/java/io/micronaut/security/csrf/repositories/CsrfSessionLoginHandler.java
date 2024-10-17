package io.micronaut.security.csrf.repositories;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.type.Argument;
import io.micronaut.core.util.functional.ThrowingSupplier;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.config.RedirectConfiguration;
import io.micronaut.security.config.RedirectService;
import io.micronaut.security.csrf.generator.CsrfTokenGenerator;
import io.micronaut.security.csrf.validator.CsrfTokenValidator;
import io.micronaut.security.errors.PriorToLoginPersistence;
import io.micronaut.security.filters.SecurityFilter;
import io.micronaut.security.handlers.RedirectingLoginHandler;
import io.micronaut.security.session.SessionAuthenticationModeCondition;
import io.micronaut.security.session.SessionLoginHandler;
import io.micronaut.session.Session;
import io.micronaut.session.SessionStore;
import io.micronaut.session.http.SessionForRequest;
import jakarta.inject.Singleton;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Requires(
        condition = SessionAuthenticationModeCondition.class
)
@Replaces(SessionLoginHandler.class)
@Singleton
public class CsrfSessionLoginHandler implements RedirectingLoginHandler<HttpRequest<?>, MutableHttpResponse<?>>,
        CsrfRepository<HttpRequest<?>>,
        CsrfTokenValidator<HttpRequest<?>> {
    protected final @Nullable String loginSuccess;
    protected final @Nullable String loginFailure;
    protected final RedirectConfiguration redirectConfiguration;
    protected final SessionStore<Session> sessionStore;
    private final PriorToLoginPersistence<HttpRequest<?>, MutableHttpResponse<?>> priorToLoginPersistence;
    private final CsrfTokenGenerator<HttpRequest<?>> csrfTokenGenerator;
    private static final String SESSION_KEY_CSRF = "csrf";

    public CsrfSessionLoginHandler(RedirectConfiguration redirectConfiguration,
                                   SessionStore<Session> sessionStore,
                                   @Nullable PriorToLoginPersistence<HttpRequest<?>,
                                           MutableHttpResponse<?>> priorToLoginPersistence,
                                   RedirectService redirectService,
                                   CsrfTokenGenerator<HttpRequest<?>> csrfTokenGenerator) {
        this.loginFailure = redirectConfiguration.isEnabled() ? redirectService.loginFailureUrl() : null;
        this.loginSuccess = redirectConfiguration.isEnabled() ? redirectService.loginSuccessUrl() : null;
        this.redirectConfiguration = redirectConfiguration;
        this.sessionStore = sessionStore;
        this.priorToLoginPersistence = priorToLoginPersistence;
        this.csrfTokenGenerator = csrfTokenGenerator;
    }

    public MutableHttpResponse<?> loginSuccess(Authentication authentication, HttpRequest<?> request) {
        this.saveAuthenticationInSession(authentication, request);
        return this.loginSuccessResponse(request);
    }

    public MutableHttpResponse<?> loginRefresh(Authentication authentication, String refreshToken, HttpRequest<?> request) {
        throw new UnsupportedOperationException("Session based logins do not support refresh");
    }

    public MutableHttpResponse<?> loginFailed(AuthenticationResponse authenticationResponse, HttpRequest<?> request) {
        if (this.loginFailure == null) {
            return HttpResponse.ok();
        } else {
            UriBuilder builder = UriBuilder.of(this.loginFailure);
            if (authenticationResponse instanceof AuthenticationFailed authenticationFailed) {
                builder.queryParam("reason", authenticationFailed.getReason());
            }
            URI location = builder.build();
            return HttpResponse.seeOther(location);
        }
    }

    private @NonNull MutableHttpResponse<?> loginSuccessResponse(HttpRequest<?> request) {
        if (this.loginSuccess == null) {
            return HttpResponse.ok();
        } else {
            try {
                MutableHttpResponse<?> response = HttpResponse.status(HttpStatus.SEE_OTHER);
                response.getHeaders().location((URI)this.loginSuccessUriSupplier(this.loginSuccess, request, response).get());
                return response;
            } catch (URISyntaxException var3) {
                return HttpResponse.serverError();
            }
        }
    }

    private @NonNull ThrowingSupplier<URI, URISyntaxException> loginSuccessUriSupplier(@NonNull String loginSuccess, HttpRequest<?> request, @NonNull MutableHttpResponse<?> response) {
        ThrowingSupplier<URI, URISyntaxException> uriSupplier = () -> {
            return new URI(loginSuccess);
        };
        if (this.priorToLoginPersistence != null) {
            Optional<URI> originalUri = this.priorToLoginPersistence.getOriginalUri(request, response);
            if (originalUri.isPresent()) {
                Objects.requireNonNull(originalUri);
                uriSupplier = originalUri::get;
            }
        }

        return uriSupplier;
    }

    protected Session saveAuthenticationInSession(Authentication authentication, HttpRequest<?> request) {
        Session session = findOrCreateSessionByRequest(request);
        session.put(SecurityFilter.AUTHENTICATION, authentication);
        session.put(SESSION_KEY_CSRF, csrfTokenGenerator.generate(request));
        return session;
    }

    /**
     *
     * @param request Request
     * @return A session associated with this request or a newly created session.
     */
    protected Session findOrCreateSessionByRequest(HttpRequest<?> request) {
        return SessionForRequest.find(request)
                .orElseGet(() -> SessionForRequest.create(this.sessionStore, request));
    }

    @Override
    @NonNull
    public Optional<String> findCsrfToken(@Nullable HttpRequest<?> request) {
        Session session = findOrCreateSessionByRequest(request);
        return session.get(SESSION_KEY_CSRF, String.class);
    }

    @Override
    public boolean validateCsrfToken(@Nullable HttpRequest<?> request, @NonNull String token) {
        return findCsrfToken(request).map(sessionToken -> sessionToken.equals(token)).orElse(false);
    }
}
