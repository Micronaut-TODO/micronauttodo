package com.micronauttodo.security.dbauth;

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Singleton
public class DefaultUserRepository implements UserRepository {
    private final PasswordEncoder passwordEncoder;
    private final UserCrudRepository userCrudRepository;

    public DefaultUserRepository(PasswordEncoder passwordEncoder,
                                 UserCrudRepository userCrudRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userCrudRepository = userCrudRepository;
    }

    @Override
    public void save(@NonNull @NotNull @Valid SignUpForm signUpForm) {
        String encodedPassword = passwordEncoder.encode(signUpForm.password());
        userCrudRepository.save(new UserEntity(null, signUpForm.email(), encodedPassword));
    }
}
