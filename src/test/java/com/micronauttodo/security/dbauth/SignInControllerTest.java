package com.micronauttodo.security.dbauth;

import com.micronauttodo.BrowserRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
@MicronautTest(transactional = false)
class SignInControllerTest {

    @Test
    void login(@Client("/")HttpClient httpClient) {
        BlockingHttpClient client = httpClient.toBlocking();
        String html = assertDoesNotThrow(() -> client.retrieve(BrowserRequest.GET("/security/login")));
        assertNotNull(html);
    }

    @Test
    void loginFailed(@Client("/")HttpClient httpClient) {
        BlockingHttpClient client = httpClient.toBlocking();
        String html = assertDoesNotThrow(() -> client.retrieve(BrowserRequest.GET("/security/login/failed")));
        assertNotNull(html);
    }
}