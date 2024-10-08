package com.micronauttodo.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;

import java.util.Collections;
import java.util.Map;

@Controller("/unauthorized")
public class UnauthorizedController {
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Produces(MediaType.TEXT_HTML)
    @Get
    @View("unauthorized")
    Map<String, Object> index() {
        return Collections.emptyMap();
    }
}
