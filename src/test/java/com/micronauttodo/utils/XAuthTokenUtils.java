package com.micronauttodo.utils;

import io.micronaut.http.MutableHttpRequest;

public final class XAuthTokenUtils {
    private XAuthTokenUtils() {
    }

    public static MutableHttpRequest<?> decorate(MutableHttpRequest<?> request, String username) {
        return request.header(XAuthTokenAuthenticationFetcher.X_AUTH_TOKEN, username);
    }
}
