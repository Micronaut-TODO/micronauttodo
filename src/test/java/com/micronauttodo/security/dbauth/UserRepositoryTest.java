package com.micronauttodo.security.dbauth;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers(disabledWithoutDocker = true)
@MicronautTest(startApplication = false, transactional = false)
class UserRepositoryTest {

    @Test
    void saveUser(UserCrudRepository userCrudRepository, UserRepository userRepository) {
        String email = "admin@example.com";
        long count = userCrudRepository.count();
        userRepository.save(new SignUpForm("admin@example.com", "admin123", "admin123"));
        assertEquals(1 + count, userCrudRepository.count());
        assertTrue(userCrudRepository.findAll().stream().anyMatch(user -> user.username().equals(email) && !user.password().equals("admin123")));
        userCrudRepository.deleteAll();
    }

}