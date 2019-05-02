package io.lishman.green.service;

import io.lishman.green.model.User;
import io.lishman.green.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringJUnitConfig(classes = {
        UserService.class,
        UserServiceSpringJUnitTest.Config.class
})
class UserServiceSpringJUnitTest {

    @TestConfiguration
    static class Config {

        @Bean
        public UserRepository userRepository() {
            return Mockito.mock(UserRepository.class);
        }
    }

    @Autowired
    private UserService userService;

    @Nested
    @DisplayName("getAllUsers() method")
    class GetAllUsers {

        @Test
        @DisplayName("Given there are no users, then an empty list is returned")
        void givenThereAreNoUsersThenAnEmptyListIsReturned() {
            final List<User> actual = userService.getAllUsers();
            assertThat(actual, hasSize(0));
        }
    }
}
