// Copyright 2024 Object Computing, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

//     http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.micronauttodo.security.dbauth;

import io.micronaut.serde.annotation.Serdeable;
import io.micronaut.views.fields.annotations.InputPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Serdeable
public record LoginForm(
        /**
         * This must be username to match {@link UsernamePasswordCredentials#getUsername()}
         */
        @NotBlank
        @Email
        String username,

        @InputPassword
        @NotBlank
        String password) {
}