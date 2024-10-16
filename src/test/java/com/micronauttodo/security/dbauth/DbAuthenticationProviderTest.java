package com.micronauttodo.security.dbauth;

import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(startApplication = false, transactional = false)
class DbAuthenticationProviderTest {

    @Test
    void saveUser(DbAuthenticationProvider<?> authenticationProvider,
                  UserCrudRepository userCrudRepository,
                  UserRepository userRepository) {
        String email = "admin@example.com";
        String password = "admin123";
        userRepository.save(new SignUpForm(email, password, password));
        AuthenticationResponse authenticationResponse = authenticationProvider.authenticate(null, new UsernamePasswordCredentials(email, password));
        assertTrue(authenticationResponse.isAuthenticated());
        authenticationResponse = authenticationProvider.authenticate(null, new UsernamePasswordCredentials(email, "bogus"));
        assertFalse(authenticationResponse.isAuthenticated());
        userCrudRepository.deleteAll();
    }

}