package io.micronaut.security.csrf.resolver;

import com.micronauttodo.BrowserRequest;
import com.micronauttodo.MicronautTestNoPersistence;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.csrf.repositories.CsrfRepository;
import io.micronaut.security.csrf.validator.CsrfTokenValidator;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Property(name = "spec.name", value = "FieldCsrfTokenResolverTest")
@MicronautTestNoPersistence
class FieldCsrfTokenResolverTest {

    @Inject
    EmbeddedServer embeddedServer;
    @Test
    void fieldTokenResolver(@Client("/") HttpClient httpClient) {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> request = BrowserRequest.POST("/password/change", "username=sherlock&csrfToken=abcde&password=elementary", embeddedServer);
        String result = assertDoesNotThrow(() -> client.retrieve(request));
        assertEquals("sherlock", result);
    }

    @Requires(property = "spec.name", value = "FieldCsrfTokenResolverTest")
    @Singleton
    @Replaces(CsrfTokenValidator.class)
    static class CsrfTokenValidatorReplacement implements CsrfTokenValidator<HttpRequest<?>> {
        @Override
        public boolean validateCsrfToken(HttpRequest<?> request, String token) {
            return token.equals("abcde");
        }
    }

    @Requires(property = "spec.name", value = "FieldCsrfTokenResolverTest")
    @Controller
    static class PasswordChangeController {
        @Secured(SecurityRule.IS_ANONYMOUS)
        @Produces(MediaType.TEXT_HTML)
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        @Post("/password/change")
        String changePassword(@Body PasswordChangeForm passwordChangeForm) {
            return passwordChangeForm.username;
        }
    }

    @Serdeable
    record PasswordChangeForm(
        String username,
        String password,
        String csrfToken) {
    }
}