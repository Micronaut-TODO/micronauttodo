package com.micronauttodo.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Hidden;

import static com.micronauttodo.controllers.TodoController.URI_TODO_LIST;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller
class HomeController {
    @Hidden
    @Produces(MediaType.TEXT_HTML)
    @Get
    HttpResponse<?> index() {
       return HttpResponse.seeOther(URI_TODO_LIST);
    }
}
