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

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailureReason;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.HttpRequestExecutorAuthenticationProvider;
import jakarta.inject.Singleton;

import java.util.Map;
import java.util.Optional;

@Requires(property = "datasources.default.enabled", notEquals = StringUtils.FALSE, defaultValue = StringUtils.TRUE)
@Singleton
class DbAuthenticationProvider<B> implements HttpRequestExecutorAuthenticationProvider<B> {
    public static final String ATTRIBUTE_EMAIL = "email";
    private final UserCrudRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    DbAuthenticationProvider(UserCrudRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public @NonNull AuthenticationResponse authenticate(@Nullable HttpRequest<B> requestContext,
                                                        @NonNull AuthenticationRequest<String, String> authRequest) {
        if (authRequest.getIdentity() == null) {
            return AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND);
        }
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(authRequest.getIdentity());
        if (userEntityOptional.isEmpty()) {
            return AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND);
        }
        UserEntity userEntity = userEntityOptional.get();
        if (authRequest.getSecret() == null) {
            return AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH);
        }
        if (!passwordEncoder.matches(authRequest.getSecret(), userEntity.password())) {
            return AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH);
        }
        return AuthenticationResponse.success("" + userEntity.id(), Map.of(ATTRIBUTE_EMAIL, userEntity.username()));
    }
}
