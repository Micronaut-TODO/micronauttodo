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
    public static final String ACTION_SLASH = "/";
    public static final String ACTION_LIST = "list";
    public static final String ACTION_SAVE = "save";
    public static final String ACTION_DELETE = "delete";
    public static final String PATH_SAVE = ACTION_SLASH + ACTION_SAVE;
    public static final String PATH_DELETE = ACTION_SLASH + ACTION_DELETE;
    public static final String PATH_LIST = ACTION_SLASH + ACTION_LIST;

    private Api() {
    }
}
