package io.lishman.green.service;

import io.lishman.green.entity.UserEntity;
import io.lishman.green.exception.UserResourceNotFoundException;
import io.lishman.green.model.User;
import io.lishman.green.repository.UserRepository;
import io.lishman.green.testing.annotations.ServiceIntegrationTest;
import io.lishman.green.testing.fixtures.UserFixture;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.lishman.green.testing.matchers.UserMatcher.matchesUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

class UserServiceTest {

    private static final long USER_ID = 10L;

    @Nested
    @ServiceIntegrationTest
    @DisplayName("getAllUsers() method")
    class GetAllUsers {

        @Autowired
        private UserService userService;

        @Test
        @DisplayName("Given there are no users, then an empty list is returned")
        void noUsersInList() {
            final List<User> actual = userService.getAllUsers();
            assertThat(actual, hasSize(0));
        }

        @Test
        @DisplayName("Given there is one user, then a list with one is returned")
        void singleUserInList(@Autowired final UserRepository userRepository) {
            final List<UserEntity> userEntities = Collections.singletonList(UserFixture.leanneGrahamEntity());
            given(userRepository.findAll()).willReturn(userEntities);

            final List<User> actualUsers = userService.getAllUsers();

            assertThat(actualUsers, hasSize(1));
            assertThat(actualUsers.get(0), matchesUser(UserFixture.leanneGraham()));
        }

        @Test
        @DisplayName("Given there are several users, then a list with all of them is returned")
        void usersInList(@Autowired final UserRepository userRepository) {
            final List<UserEntity> userEntities = List.of(UserFixture.leanneGrahamEntity(), UserFixture.nicholasRunolfsdottirEntity());
            given(userRepository.findAll()).willReturn(userEntities);

            final List<User> actualUsers = userService.getAllUsers();

            assertThat(actualUsers, hasSize(2));
            assertThat(actualUsers, Matchers.contains(
                    matchesUser(UserFixture.leanneGraham()),
                    matchesUser(UserFixture.nicholasRunolfsdottir()))
            );

        }
    }

    @Nested
    @ServiceIntegrationTest
    @DisplayName("getAllUserById(Long) method")
    class GetUserBy {

        @Autowired
        private UserService userService;

        @Test
        @DisplayName("Given there is no user with the id, then an exception is thrown")
        void userNotFoundById(@Autowired final UserRepository userRepository) {
            given(userRepository.findById(USER_ID)).willReturn(Optional.empty());

            UserResourceNotFoundException thrown =
                    assertThrows(UserResourceNotFoundException.class,
                            () -> userService.getUserById(USER_ID),
                            "Expected getUserById() to throw, but it didn't");

            assertThat(thrown.getMessage(), is(equalTo(String.format("User %s not found", USER_ID))));
        }

        @Test
        @DisplayName("Given there is a user with the id, then this user is returned")
        void userFoundById(@Autowired final UserRepository userRepository) {
            given(userRepository.findById(USER_ID)).willReturn(Optional.of(UserFixture.nicholasRunolfsdottirEntity()));

            final User actualUser = userService.getUserById(USER_ID);

            assertThat(actualUser, matchesUser(UserFixture.nicholasRunolfsdottir()));
        }
    }
}
