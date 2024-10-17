package io.micronaut.security.csrf.filter;

import com.micronauttodo.BrowserRequest;
import com.micronauttodo.MicronautTestNoPersistence;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.core.order.Ordered;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.csrf.generator.CsrfTokenGenerator;
import io.micronaut.security.csrf.validator.CsrfTokenValidator;
import io.micronaut.security.filters.AuthenticationFetcher;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@Property(name = "micronaut.http.client.follow-redirects", value = StringUtils.FALSE)
@Property(name = "spec.name", value = "CsrfTokenFilterTest")
@MicronautTestNoPersistence
class CsrfTokenFilterTest {
    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void transferIsRejectedByCsrfFilter() {
        HttpResponse<?> response = bankTransfer(httpClient, new Transfer("evil", 50));
        assertEquals(HttpStatus.SEE_OTHER, response.getStatus());
        String location = response.getHeaders().get(HttpHeaders.LOCATION);
        assertNotNull(location);
    }

    @Test
    void transferIsRejectedWithWrongByCsrfFilter() {
        HttpResponse<?> response = bankTransfer(httpClient, new TransferForm("evil", 50, "12345"));
        assertEquals(HttpStatus.SEE_OTHER, response.getStatus());
        String location = response.getHeaders().get(HttpHeaders.LOCATION);
        assertNotNull(location);
    }

    @Test
    void transferIsNotRejectedWithCorrectCsrfToken() {
        HttpResponse<?> response = bankTransfer(httpClient, new TransferForm("evil", 50, "abcde"));
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    private HttpResponse<?> bankTransfer(HttpClient httpClient, Object body) {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> request = BrowserRequest.POST("/bank/transfer", body);
        return assertDoesNotThrow(() -> client.exchange(request));
    }

    @Requires(property = "spec.name", value = "CsrfTokenFilterTest")
    @Replaces(CsrfTokenValidator.class)
    @Singleton
    static class CsrfTokenValidatorReplacement implements CsrfTokenValidator {

        private final String csrfToken = "abcde";

        @Override
        public boolean validateCsrfToken(Object request, String token) {
            return csrfToken.equals(token);
        }
    }

    @Requires(property = "spec.name", value = "CsrfTokenFilterTest")
    @Replaces(CsrfTokenGenerator.class)
    @Singleton
    static class CsrfTokenGeneratorReplacement implements CsrfTokenGenerator {

        private final String csrfToken = "abcde";
        @Override
        public String generate(Object request) {
            return csrfToken;
        }
    }

    @Requires(property = "spec.name", value = "CsrfTokenFilterTest")
    @Singleton
    static class MockAuthenticationFetcher implements AuthenticationFetcher {
        @Override
        public Publisher<Authentication> fetchAuthentication(Object request) {
            return Publishers.just(Authentication.build("sherlock"));
        }
    }

    @Requires(property = "spec.name", value = "CsrfTokenFilterTest")
    @Controller("/bank")
    static class BankController {
        AtomicInteger balance = new AtomicInteger(100);

        @Secured(SecurityRule.IS_AUTHENTICATED)
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        @Produces(MediaType.TEXT_HTML)
        @Post("/transfer")
        String transfer(@Body Transfer to) {
            balance.updateAndGet(i -> i - to.amount);
            return "<!DOCTYPE html><html><head><title>Transfer Successful</title><body></body></html>";
        }
    }

    @Serdeable
    record Transfer(
        String to,
        Integer amount) {
    }

    @Serdeable
    record TransferForm(
        String to,
        Integer amount,
        String csrfToken) {
    }
}