package com.micronauttodo.controllers.api.v1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "micronauttodo",
                version = "1.0"
        ), servers = @Server(url = "https://micronauttodo.com")
)
public final class Api {
    private Api() {

    }
}
