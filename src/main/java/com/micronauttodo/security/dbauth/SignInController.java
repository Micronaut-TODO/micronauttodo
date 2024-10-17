/*
 * Copyright 2017-2024 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.micronauttodo.security.dbauth;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.endpoints.LoginControllerConfiguration;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;
import io.micronaut.views.fields.FieldsetGenerator;
import io.micronaut.views.fields.Form;
import io.micronaut.views.fields.FormGenerator;
import io.micronaut.views.fields.elements.InputSubmitFormElement;
import io.micronaut.views.fields.messages.Message;

import java.util.Map;

@Controller
class SignInController {
    public static final String PATH_LOGIN = "/security/login";
    private static final @NonNull Message MESSAGE_LOG_IN = Message.of("Sign in", "loginform.submit");
    private static final @NonNull InputSubmitFormElement INPUT_SUBMIT_LOGIN = InputSubmitFormElement.builder().value(MESSAGE_LOG_IN).build();
    public static final String MODEL_LOGIN_FORM = "form";
    public static final String VIEW_SECURITY_FORM = "/security/login";

    private final FormGenerator formGenerator;
    private final LoginControllerConfiguration loginControllerConfiguration;

    SignInController(FormGenerator formGenerator,
                     LoginControllerConfiguration loginControllerConfiguration) {
        this.formGenerator = formGenerator;
        this.loginControllerConfiguration = loginControllerConfiguration;
    }

    @Produces(MediaType.TEXT_HTML)
    @Secured(SecurityRule.IS_ANONYMOUS)
    @View(VIEW_SECURITY_FORM)
    @Get(PATH_LOGIN)
    Map<String, Object> login() {
        Form form = formGenerator.generate(loginControllerConfiguration.getPath(), LoginForm.class, INPUT_SUBMIT_LOGIN);
        // Disable turbo for login form
        form = new Form(form.action(), form.method(), form.fieldset(), form.enctype(), false);
        return Map.of(MODEL_LOGIN_FORM, form);
    }
}
