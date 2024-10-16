package io.micronaut.security.csrf.views;

import com.micronauttodo.views.MapViewModelProcessor;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.csrf.repositories.CsrfRepository;
import io.micronaut.security.filters.SecurityFilter;
import jakarta.inject.Singleton;

import java.util.Map;

@Singleton
public class CsrfTokenViewModelProcessor extends MapViewModelProcessor {
    public static final String MODEL_CSRF_TOKEN = "csrfToken";
    private final CsrfRepository<HttpRequest<?>> csrfRepository;

    public CsrfTokenViewModelProcessor(CsrfRepository<HttpRequest<?>> csrfRepository) {
        this.csrfRepository = csrfRepository;
    }

    @Override
    protected void populateModel(HttpRequest<?> request, Map<String, Object> viewModel) {
        request.getAttribute(SecurityFilter.AUTHENTICATION, Authentication.class)
                .ifPresent(authentication ->
                        viewModel.putIfAbsent(MODEL_CSRF_TOKEN, csrfRepository.findOrGenerateCsrfToken(request))
                );
    }
}
