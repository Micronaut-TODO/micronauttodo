package io.micronaut.security.csrf.repositories;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.csrf.generator.CsrfTokenGenerator;
import io.micronaut.session.Session;
import io.micronaut.session.SessionStore;
import io.micronaut.session.http.SessionForRequest;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Requires(beans = SessionStore.class)
@Singleton
public class SessionCsrfRepository implements CsrfRepository<HttpRequest<?>> {
    private final SessionStore<Session> sessionStore;
    private static final String SESSION_KEY_CSRF = "csrf";
    private final CsrfTokenGenerator<HttpRequest<?>> csrfTokenGenerator;

    public SessionCsrfRepository(SessionStore<Session> sessionStore,
                                 CsrfTokenGenerator<HttpRequest<?>> csrfTokenGenerator) {
        this.sessionStore = sessionStore;
        this.csrfTokenGenerator = csrfTokenGenerator;
    }


    @Override
    public Optional<String> findCsrfToken(HttpRequest<?> request) {
        return csrfTokensInSession(request).stream().findFirst();
    }

    @Override
    public String generate(HttpRequest<?> request) {
        String token = csrfTokenGenerator.generate(request);
        saveCsrfTokenInSession(token, request);
        return token;
    }

    private void saveCsrfTokenInSession(String csrfToken, HttpRequest<?> request) {
        Session session = sessionForRequest(request);
       List<String> csrfTokens = csrfTokensInSession(session);
        csrfTokens.add(csrfToken);
        session.put(SESSION_KEY_CSRF, csrfTokens);
    }

    private Session sessionForRequest(HttpRequest<?> request) {
        return SessionForRequest.find(request)
                .orElseGet(() -> SessionForRequest.create(sessionStore, request));
    }
    private List<String> csrfTokensInSession(HttpRequest<?> request) {
        Session session = sessionForRequest(request);
        return csrfTokensInSession(session);
    }

    List<String> csrfTokensInSession(Session session) {
        return session.get(SESSION_KEY_CSRF, Argument.listOf(String.class)).orElseGet(ArrayList::new);
    }
}
