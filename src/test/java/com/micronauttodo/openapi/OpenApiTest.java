package com.micronauttodo.openapi;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class OpenApiTest {
    @Test
    void openApiIsGenerated(@Client("/") HttpClient httpClient) {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> request = HttpRequest.GET("/swagger/micronauttodo-1.0.yml");
        HttpResponse<String> response = assertDoesNotThrow(() -> client.exchange(request, String.class));
        assertEquals(HttpStatus.OK, response.getStatus());
        String yml = response.body();
        assertNotNull(yml);
        assertFalse(yml.contains("/views/question/list"));
    }
}
