package com.micronauttodo.controllers.locale;

import com.micronauttodo.BrowserRequest;
import com.micronauttodo.MicronautTestNoPersistence;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Property(name = "micronaut.http.client.follow-redirects", value = StringUtils.FALSE)
@MicronautTestNoPersistence
class LocaleControllerTest {

    @Test
    void localeSavesACooke(@Client("/") HttpClient httpClient) {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> request = BrowserRequest.POST("/locale", Map.of("locale", "es"));
        HttpResponse<?> response = assertDoesNotThrow(() -> client.exchange(request));
        assertEquals(HttpStatus.SEE_OTHER, response.status());
        final String cookieName = "locale";
        assertTrue(response.getCookie(cookieName).isPresent());
        assertEquals("es", response.getCookie(cookieName).get().getValue());
    }
}