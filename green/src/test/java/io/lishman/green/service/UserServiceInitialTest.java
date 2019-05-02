package io.lishman.green.service;

import io.lishman.green.model.User;
import io.lishman.green.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
class UserServiceInitialTest {

    @Configuration
    static class Config {

        @Bean
        public UserService userService() {
            return new UserService(userRepository);
        }

        @MockBean
        private UserRepository userRepository;
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
