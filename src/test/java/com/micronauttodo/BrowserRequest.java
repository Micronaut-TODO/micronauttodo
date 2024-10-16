package com.micronauttodo;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequestFactory;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.runtime.server.EmbeddedServer;
import java.net.URI;
import java.util.Map;
import java.util.Objects;

public final class BrowserRequest {
    EmbeddedServer embeddedServer;
    private BrowserRequest() {
    }

    public static <T> MutableHttpRequest<T> POST(URI uri, T body, @Nullable EmbeddedServer embeddedServer) {
        return POST(uri.toString(), body, embeddedServer);
    }

    public static <T> MutableHttpRequest<T> POST(URI uri, T body) {
        return POST(uri.toString(), body);
    }

    public static <T> MutableHttpRequest<T> POST(String uri, T body, @Nullable EmbeddedServer embeddedServer) {
        Objects.requireNonNull(uri, "Argument [uri] is required");
        MutableHttpRequest<T> request = HttpRequestFactory.INSTANCE.post(uri, body)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_HTML);
        return decorateWithReferer(decorateWithOrigin(request, embeddedServer), embeddedServer);
    }

    public static <T> MutableHttpRequest<T> POST(String uri, T body) {
        return POST(uri, body, null);
    }

    public static <T> MutableHttpRequest<T> POST(String uri, Map<CharSequence, CharSequence> headers, T body) {
        return POST(uri, body).headers(headers);
    }

    public static MutableHttpRequest<?> GET(String uri, @Nullable EmbeddedServer embeddedServer) {
        MutableHttpRequest<?> request = HttpRequestFactory.INSTANCE.get(uri)
                .accept(MediaType.TEXT_HTML);
        return decorateWithReferer(decorateWithOrigin(request, embeddedServer), embeddedServer);
    }

    private static <T> MutableHttpRequest<T> decorateWithOrigin(MutableHttpRequest<T> request,
                                                          @Nullable EmbeddedServer embeddedServer) {
        if (embeddedServer != null) {
            //return request.header(HttpHeaders.ORIGIN, embeddedServer.getHost());
        }
        return request;
    }

    private static <T> MutableHttpRequest<T> decorateWithReferer(MutableHttpRequest<T> request,
                                                              @Nullable EmbeddedServer embeddedServer) {
        if (embeddedServer != null) {
            //return request.header(HttpHeaders.REFERER, embeddedServer.getURL().toString());
        }
        return request;
    }

    public static MutableHttpRequest<?> GET(String uri) {
        EmbeddedServer embeddedServer = null;
        return GET(uri, embeddedServer);
    }

    public static MutableHttpRequest<?> GET(String uri, Map<CharSequence, CharSequence> headers) {
        return GET(uri).headers(headers);
    }

    public static MutableHttpRequest<?> GET(URI uri) {
        return GET(uri.toString());
    }

}
