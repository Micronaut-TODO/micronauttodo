package com.micronauttodo.controllers;

import com.micronauttodo.AssertionUtils;
import com.micronauttodo.BrowserRequest;
import com.micronauttodo.entities.TodoEntity;
import com.micronauttodo.repositories.TodoCrudRepository;
import com.micronauttodo.utils.XAuthTokenUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.multitenancy.tenantresolver.SystemPropertyTenantResolverConfigurationProperties;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@MicronautTest(transactional = false)
class TodoControllerTest {
    private static final Logger LOG = LoggerFactory.getLogger(TodoControllerTest.class);

    @Test
    void todoCrud(@Client("/")HttpClient httpClient,
                  TodoCrudRepository todoCrudRepository) {
        BlockingHttpClient client = httpClient.toBlocking();
        String username = "sdelamo";
        HttpRequest<?> saveRequest = XAuthTokenUtils.decorate(BrowserRequest.POST(URI.create("/todo/save"), Map.of("item", "Learn GraalVM")), username);
        String html = assertDoesNotThrow(() -> client.retrieve(saveRequest));
        AssertionUtils.assertHtmlContains(LOG, html, "<!DOCTYPE html>");
        AssertionUtils.assertHtmlContains(LOG, html, "Learn GraalVM");

        System.setProperty(SystemPropertyTenantResolverConfigurationProperties.DEFAULT_SYSTEM_PROPERTY_NAME, username);

        TodoEntity entity = todoCrudRepository.findAll().stream().filter(e -> e.item().equals("Learn GraalVM")).findFirst().orElseThrow();
        HttpRequest<?> deleteRequest = XAuthTokenUtils.decorate(BrowserRequest.POST(URI.create("/todo/delete"), Map.of("id", entity.id())), username);
        html = assertDoesNotThrow(() -> client.retrieve(deleteRequest));
        AssertionUtils.assertHtmlContains(LOG, html, "<!DOCTYPE html>");
        AssertionUtils.assertHtmlDoesNotContain(LOG, html, "Learn GraalVM");
    }
}