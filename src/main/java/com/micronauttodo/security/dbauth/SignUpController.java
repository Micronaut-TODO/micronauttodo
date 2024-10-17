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
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.ModelAndView;
import io.micronaut.views.View;
import io.micronaut.views.fields.Form;
import io.micronaut.views.fields.FormGenerator;
import io.micronaut.views.fields.elements.InputSubmitFormElement;
import io.micronaut.views.fields.messages.Message;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

@Controller
class SignUpController {
    private static final String PATH_SIGNUP = "/security/signup";
    private static final String VIEW_SIGNUP = "/security/signup";

    public static final @NonNull Message MESSAGE_SIGN_UP = Message.of("Sign Up", "signupform.submit");
    public static final @NonNull InputSubmitFormElement INPUT_SUBMIT_SIGN_UP = InputSubmitFormElement.builder().value(MESSAGE_SIGN_UP).build();
    private static final String MODEL_SIGNUP_FORM = "form";
    private final FormGenerator formGenerator;
    private final UserRepository userRepository;

    SignUpController(FormGenerator formGenerator,
                     UserRepository userRepository) {
        this.formGenerator = formGenerator;
        this.userRepository = userRepository;
    }

    @Produces(MediaType.TEXT_HTML)
    @Secured(SecurityRule.IS_ANONYMOUS)
    @View(VIEW_SIGNUP)
    @Get(PATH_SIGNUP)
    Map<String, Object> signup() {
        Form form = formGenerator.generate(PATH_SIGNUP, SignUpForm.class, INPUT_SUBMIT_SIGN_UP);
        // Disable turbo for sign up form
        form = new Form(form.action(), form.method(), form.fieldset(), form.enctype(), false);
        return Map.of(MODEL_SIGNUP_FORM, form);
    }

    @Produces(MediaType.TEXT_HTML)
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post(PATH_SIGNUP)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    HttpResponse<?> signup(@Valid @Body SignUpForm signUpForm) {
        userRepository.save(signUpForm);
        return HttpResponse.seeOther(URI.create(SignInController.PATH_LOGIN));
    }

    @Error(exception = ConstraintViolationException.class)
    public HttpResponse<?> onConstraintViolationException(HttpRequest<?> request,
                                                          @Nullable Authentication authentication,
                                                          ConstraintViolationException ex) {
        if (request.getPath().equals(PATH_SIGNUP)) {
            return request.getBody(SignUpForm.class)
                    .map(signUpForm -> {
                        Form form = formGenerator.generate(PATH_SIGNUP, signUpForm, ex);
                        return HttpResponse.ok()
                                .body(new ModelAndView<>(VIEW_SIGNUP,
                                        Collections.singletonMap(MODEL_SIGNUP_FORM, form)));
                    })
                    .orElseGet(HttpResponse::serverError);
        }
        return HttpResponse.serverError();
    }
}
