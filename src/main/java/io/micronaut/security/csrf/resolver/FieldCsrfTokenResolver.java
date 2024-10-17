package io.micronaut.security.csrf.resolver;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.ServerHttpRequest;
import io.micronaut.http.body.ByteBody;
import io.micronaut.http.body.CloseableByteBody;
import io.micronaut.security.csrf.conf.CsrfConfiguration;
import jakarta.inject.Singleton;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Singleton
public class FieldCsrfTokenResolver implements CsrfTokenResolver<HttpRequest<?>> {
    private final CsrfConfiguration csrfConfiguration;

    public FieldCsrfTokenResolver(CsrfConfiguration csrfConfiguration) {
        this.csrfConfiguration = csrfConfiguration;
    }

    @Override
    public Optional<String> resolveToken(HttpRequest<?> request) {
        if (request instanceof ServerHttpRequest<?> serverHttpRequest) {
            return resolveToken(serverHttpRequest);
        }
        return Optional.empty();
    }

    public Optional<String> resolveToken(ServerHttpRequest<?> request) {
        try (CloseableByteBody ourCopy =
                     request.byteBody()
                             .split(ByteBody.SplitBackpressureMode.SLOWEST)
                             .allowDiscard()) {
            try(InputStream inputStream = ourCopy.toInputStream()) {
                String str = ofInputStream(inputStream);
                return extractCsrfTokenFromAFormUrlEncodedString(str);
            } catch (IOException e) {
                return Optional.empty();
            }
        }
    }

    private String ofInputStream(InputStream inputStream) throws IOException {
        final ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = inputStream.read(buffer)) != -1; ) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8);
    }

    private Optional<String> extractCsrfTokenFromAFormUrlEncodedString(String body) {
        final String[] arr = body.split("&");
        final String prefix = csrfConfiguration.getFieldName() + "=";
        for (String s : arr) {
            if (s.startsWith(prefix)) {
                return Optional.of(s.substring(prefix.length()));
            }
        }
        return Optional.empty();
    }
}
