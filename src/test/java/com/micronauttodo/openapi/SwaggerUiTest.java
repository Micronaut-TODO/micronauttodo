package com.micronauttodo.openapi;

import com.micronauttodo.MicronautTestNoPersistence;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@MicronautTestNoPersistence
class SwaggerUiTest {
    @Test
    void openApi(@Client("/") HttpClient httpClient) {
        BlockingHttpClient client = httpClient.toBlocking();
        assertDoesNotThrow(() -> client.exchange("/swagger-ui/index.html"));
    }
}
