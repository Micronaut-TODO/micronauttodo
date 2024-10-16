package com.micronauttodo.security.dbauth;

import com.micronauttodo.BrowserRequest;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
class SignUpControllerTest {

    @Test
    void signupGet(@Client("/") HttpClient httpClient, UserCrudRepository userCrudRepository) {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpRequest<?> request = BrowserRequest.GET("/security/signup");
        String html = assertDoesNotThrow(() -> client.retrieve(request));
        assertNotNull(html);
    }

    @Test
    void signupPost(@Client("/") HttpClient httpClient, UserCrudRepository userCrudRepository) {
        BlockingHttpClient client = httpClient.toBlocking();
        String email = "admin@example.com";
        String password = "admin123";
        HttpRequest<?> request = BrowserRequest.POST("/security/signup", Map.of("email", email, "password", password, "repeatPassword", password));
        long count = userCrudRepository.count();
        assertDoesNotThrow(() -> client.exchange(request));
        assertEquals(1 + count, userCrudRepository.count());
        userCrudRepository.deleteAll();
    }
}