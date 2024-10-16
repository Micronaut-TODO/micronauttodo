package com.micronauttodo.controllers;

import com.micronauttodo.BrowserRequest;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Property(name = "micronaut.http.client.follow-redirects", value = StringUtils.FALSE)
@MicronautTest
class HomeControllerTest {

    @Inject
    EmbeddedServer embeddedServer;

    @Test
    void homeRedirects(@Client("/") HttpClient httpClient) {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> request = BrowserRequest.GET("/", embeddedServer);
        HttpResponse<?> response = assertDoesNotThrow(() -> client.exchange(request));
        assertEquals(HttpStatus.SEE_OTHER, response.status());
        assertTrue(response.getHeaders().contains(HttpHeaders.LOCATION));
        assertEquals("/todo/list", response.getHeaders().get(HttpHeaders.LOCATION));
    }
}