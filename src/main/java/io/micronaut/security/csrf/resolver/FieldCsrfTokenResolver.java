package io.micronaut.security.csrf.resolver;

import io.micronaut.core.convert.ConversionService;
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
    private final ConversionService conversionService;

    public FieldCsrfTokenResolver(CsrfConfiguration csrfConfiguration,
                                  ConversionService conversionService) {
        this.csrfConfiguration = csrfConfiguration;
        this.conversionService = conversionService;
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

            try {
                InputStream inputStream = ourCopy.toInputStream();
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                for (int length; (length = inputStream.read(buffer)) != -1; ) {
                    result.write(buffer, 0, length);
                }
                String formBody = result.toString(StandardCharsets.UTF_8);
                String str = csrfConfiguration.getFieldName() + "=";
                int index = formBody.indexOf(str);

                return Optional.empty();
            } catch (IOException e) {
                return Optional.empty();
            }
        }
    }
}
