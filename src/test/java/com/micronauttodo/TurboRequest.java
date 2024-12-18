package com.micronauttodo;

import io.micronaut.http.HttpRequestFactory;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.views.turbo.http.TurboMediaType;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

public final class TurboRequest {
    private TurboRequest() {
    }

    public static <T> MutableHttpRequest<T> POST(URI uri, T body) {
        return POST(uri.toString(), body);
    }

    public static <T> MutableHttpRequest<T> POST(String uri, T body) {
        Objects.requireNonNull(uri, "Argument [uri] is required");
        return HttpRequestFactory.INSTANCE.post(uri, body)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_HTML, TurboMediaType.TURBO_STREAM_TYPE);
    }

    public static <T> MutableHttpRequest<T> POST(String uri, Map<CharSequence, CharSequence> headers, T body) {
        return POST(uri, body).headers(headers);
    }

    public static MutableHttpRequest<?> GET(String uri) {
        return HttpRequestFactory.INSTANCE.get(uri).accept(MediaType.TEXT_HTML);
    }

    public static MutableHttpRequest<?> GET(String uri, Map<CharSequence, CharSequence> headers) {
        return GET(uri).headers(headers);
    }

    public static MutableHttpRequest<?> GET(URI uri) {
        return GET(uri.toString());
    }

}
