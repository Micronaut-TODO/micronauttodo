package com.micronauttodo.security.dbauth;

import com.micronauttodo.BrowserRequest;
import com.micronauttodo.MicronautTestNoPersistence;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTestNoPersistence
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