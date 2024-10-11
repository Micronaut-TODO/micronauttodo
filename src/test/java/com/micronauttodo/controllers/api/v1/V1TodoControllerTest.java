package com.micronauttodo.controllers.api.v1;

import com.micronauttodo.domains.Todo;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.type.Argument;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.micronauttodo.utils.XAuthTokenUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@Property(name = "micronaut.security.reject-not-found", value = StringUtils.FALSE)
@Property(name = "spec.name", value = "V1TodoControllerTest")
@MicronautTest
class V1TodoControllerTest {
    @Test
    void crud(@Client("/") HttpClient httpClient) {
        String userA = "sdelamo";
        String userB = "thomas";
        BlockingHttpClient client = httpClient.toBlocking();
        String path = "/api/v1/todo";

        // SAVE
        String bodyJson = "{\"item\":\"Learn about GraalVM\"}";
        HttpRequest<?> saveRequestUnauthorized = HttpRequest.POST(path, bodyJson);
        HttpClientResponseException ex = assertThrows(HttpClientResponseException.class, () -> client.exchange(saveRequestUnauthorized));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());

        HttpRequest<?> saveRequest = decorate(HttpRequest.POST(path, bodyJson), userA);
        HttpResponse<?> saveResponse = assertDoesNotThrow(() -> client.exchange(saveRequest));
        assertEquals(HttpStatus.CREATED, saveResponse.status());
        String location = saveResponse.getHeaders().get(HttpHeaders.LOCATION);
        assertNotNull(location);

        String bodyJsonMicronaut = "{\"item\":\"Learn about Micronaut\"}";
        HttpRequest<?> saveRequestUserB = decorate(HttpRequest.POST(path, bodyJsonMicronaut), userB);
        HttpResponse<?> saveResponseUserB = assertDoesNotThrow(() -> client.exchange(saveRequestUserB));
        assertEquals(HttpStatus.CREATED, saveResponseUserB.status());
        String locationItemB = saveResponseUserB.getHeaders().get(HttpHeaders.LOCATION);
        assertNotNull(locationItemB);
        String id = location.substring("/api/v1/todo/".length());

        // List
        HttpRequest<?> listRequestUserA = decorate(HttpRequest.GET("/api/v1/todo"), userA);
        HttpResponse<List<Todo>> listResponseUserA = assertDoesNotThrow(() -> client.exchange(listRequestUserA, Argument.listOf(Todo.class)));
        assertEquals(HttpStatus.OK, listResponseUserA.status());
        List<Todo> todos = listResponseUserA.body();
        assertNotNull(todos);
        assertEquals(1, todos.size());
        assertTrue(todos.stream().anyMatch(t -> t.item().equals("Learn about GraalVM")));

        HttpRequest<?> listRequestUserB = decorate(HttpRequest.GET("/api/v1/todo"), userB);
        HttpResponse<List<Todo>> listResponseUserB = assertDoesNotThrow(() -> client.exchange(listRequestUserB, Argument.listOf(Todo.class)));
        assertEquals(HttpStatus.OK, listResponseUserB.status());
        todos = listResponseUserB.body();
        assertNotNull(todos);
        assertEquals(1, todos.size());
        assertTrue(todos.stream().anyMatch(t -> t.item().equals("Learn about Micronaut")));

        // SHOW
        HttpRequest<?> getRequest = decorate(HttpRequest.GET(location), userA);
        HttpResponse<?> getResponse = assertDoesNotThrow(() -> client.exchange(getRequest));
        assertEquals(HttpStatus.OK, getResponse.status());

        HttpRequest<?> getRequestUserB = decorate(HttpRequest.GET(location), userB);
        ex = assertThrows(HttpClientResponseException.class, () -> client.exchange(getRequestUserB));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

        Optional<String> jsonOptional = getResponse.getBody(String.class);
        assertTrue(jsonOptional.isPresent());
        String json = jsonOptional.get();

        assertEquals("{\"id\":" + id + ",\"item\":\"Learn about GraalVM\"}", json);

        // DELETE
        HttpRequest<?> deleteRequestUnauthorized = HttpRequest.DELETE(location);
        ex = assertThrows(HttpClientResponseException.class, () -> client.exchange(deleteRequestUnauthorized));
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatus());

        HttpRequest<?> deleteRequest = decorate(HttpRequest.DELETE(location), userA);
        HttpResponse<?> deleteResponse = assertDoesNotThrow(() -> client.exchange(deleteRequest));
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.status());

        HttpRequest<?> deleteRequestUserB = decorate(HttpRequest.DELETE(locationItemB), userB);
        HttpResponse<?> deleteResponseUserB = assertDoesNotThrow(() -> client.exchange(deleteRequestUserB));
        assertEquals(HttpStatus.NO_CONTENT, deleteResponseUserB.status());
    }
}