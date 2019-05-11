package io.lishman.green.service;

import io.lishman.green.entity.UserEntity;
import io.lishman.green.exception.UserResourceNotFoundException;
import io.lishman.green.model.User;
import io.lishman.green.repository.UserRepository;
import io.lishman.green.testing.annotations.ServiceIntegrationTest;
import io.lishman.green.testing.fixtures.UserFixture;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DisplayName("User Service Integration Tests")
class UserServiceTest {

    private static final long MISSING_USER_ID = 99L;
    private static final long USER_ID = 10L;

    @Nested
    @ServiceIntegrationTest
    @DisplayName("getAllUsers() method")
    class GetAllUsers {

        @Autowired
        private UserService userService;

        @Test
        @DisplayName("Given there are no users, " +
                "when an attempt is made to retrieve the users, " +
                "then an empty list is returned")
        void noUsersInList() {
            final List<User> actual = userService.getAllUsers();
            assertThat(actual, hasSize(0));
        }

        @Test
        @DisplayName("Given there is one user, " +
                "when the list of users is retrieved, " +
                "then a list with only one user is returned")
        void singleUserInList(@Autowired final UserRepository userRepository) {
            final List<UserEntity> userEntities = Collections.singletonList(UserFixture.leanneGrahamEntity());
            given(userRepository.findAll()).willReturn(userEntities);

            final List<User> actualUsers = userService.getAllUsers();

            assertThat(actualUsers, hasSize(1));
            assertThat(actualUsers, Matchers.contains(matchesUser(UserFixture.leanneGraham())));
        }

        @Test
        @DisplayName("Given there are several users, " +
                "when the list of users is retrieved, " +
                "then a list with all of them is returned")
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
    @DisplayName("getUserById(Long) method")
    class GetUserById {

        @Autowired
        private UserService userService;

        @Autowired
        private UserRepository userRepository;

        @Test
        @DisplayName("Given a user exists, " +
                "when this user is retrieved using the id, " +
                "then the correct user details are returned")
        void userFoundById() {
            given(userRepository.findById(USER_ID)).willReturn(Optional.of(UserFixture.nicholasRunolfsdottirEntity()));

            final User actualUser = userService.getUserById(USER_ID);

            assertThat(actualUser, matchesUser(UserFixture.nicholasRunolfsdottir()));
        }

        @Test
        @DisplayName("Given there are some users, " +
                "when an attempt is made to retrieve a user that does not exist, " +
                "then an exception is thrown")
        void userNotFoundById() {
            given(userRepository.findById(MISSING_USER_ID)).willReturn(Optional.empty());

            UserResourceNotFoundException thrown =
                    assertThrows(UserResourceNotFoundException.class,
                            () -> userService.getUserById(USER_ID),
                            "Expected exception but none thrown");

            assertThat(thrown.getMessage(), is(equalTo(String.format("User %s not found", USER_ID))));
        }
    }

    @Nested
    @ServiceIntegrationTest
    @DisplayName("createUser(User) method")
    class createUser {

        @Autowired
        private UserService userService;

        @Autowired
        private UserRepository userRepository;

        @Test
        @DisplayName("Given there are some users, " +
                "when a new user is created, " +
                "then the new user is saved to the database " +
                "and the a user details are returned with a new id")
        void userCreated() {
            final UserEntity userToBeSavedEntity = UserFixture.bobSmithWithNullIdEntity();
            final UserEntity savedUserEntity = UserFixture.bobSmithEntity();
            given(userRepository.save(userToBeSavedEntity)).willReturn(savedUserEntity);

            final User savedUser = userService.createUser(UserFixture.bobSmithWithNullId());

            assertThat(savedUser, matchesUser(UserFixture.bobSmith()));
        }

        // TODO user already exists exception
        // TODO unique key already exists
    }

    @Nested
    @ServiceIntegrationTest
    @DisplayName("updateUser(User) method")
    class updateUser {

        @Autowired
        private UserService userService;

        @Autowired
        private UserRepository userRepository;

        @BeforeEach
        void beforeEach() {
            given(userRepository.existsById(anyLong())).willReturn(true);
        }

        @Test
        @DisplayName("Given a user exists, " +
                "when this user is updated, " +
                "then the new details are saved to the database " +
                "and the a user is returned with the new details")
        void userUpdated() {
            final UserEntity updatedUserEntity = UserFixture.leanneGrahamEntity();
            final UserEntity savedUserEntity = UserFixture.leanneGrahamEntity();
            given(userRepository.save(updatedUserEntity)).willReturn(savedUserEntity);

            final User savedUser = userService.updateUser(
                    UserFixture.leanneGraham().getId(),
                    UserFixture.leanneGraham()
            );

            assertThat(savedUser, matchesUser(UserFixture.leanneGraham()));
        }

        @Test
        @DisplayName("Given a user exists, " +
                "when an attempt is made to update a user with the wrong id in the object, " +
                "then the new details are saved to the database using the id argument " +
                "and the a user is returned with correct id in the details")
        void userUpdatedWithCorrectId() {
            final UserEntity userEntity = UserFixture.leanneGrahamEntity();
            given(userRepository.save(userEntity)).willReturn(userEntity);

            final User savedUser = userService.updateUser(
                    UserFixture.leanneGraham().getId(),
                    UserFixture.leanneGraham().cloneWithNewId(MISSING_USER_ID)
            );

            assertThat(savedUser, matchesUser(UserFixture.leanneGraham()));
        }

        @Test
        @DisplayName("Given there are some users, " +
                "when an attempt is made to update a user that does not exist, " +
                "then an exception is thrown " +
                "and no rows are updated")
        void userDoesNotExist() {
            given(userRepository.existsById(MISSING_USER_ID)).willReturn(false);

            UserResourceNotFoundException thrown =
                    assertThrows(UserResourceNotFoundException.class,
                            () -> userService.updateUser(MISSING_USER_ID, UserFixture.leanneGraham()),
                            "Expected exception but none thrown");

            assertThat(thrown.getMessage(), is(equalTo(String.format("User %s not found", MISSING_USER_ID))));
            verify(userRepository, never()).save(any(UserEntity.class));
        }

        // TODO unique key already exists

    }

    @Nested
    @ServiceIntegrationTest
    @DisplayName("deleteUser(Long) method")
    class DeleteUser {

        @Autowired
        private UserService userService;

        @Autowired
        private UserRepository userRepository;

        @Test
        @DisplayName("Given a user exists, " +
                "when this user is deleted using the id, " +
                "then the correct user row is removed from the database")
        void userRemovedById() {
            given(userRepository.existsById(anyLong())).willReturn(true);

            userService.deleteUser(USER_ID);

            verify(userRepository).deleteById(USER_ID);
        }

        @Test
        @DisplayName("Given there is a user, " +
                "when an attempt is made to retrieve a user that does not exist, " +
                "then an exception is thrown")
        void userNotRemovedById() {
            given(userRepository.existsById(MISSING_USER_ID)).willReturn(false);

            UserResourceNotFoundException thrown =
                    assertThrows(UserResourceNotFoundException.class,
                            () -> userService.deleteUser(USER_ID),
                            "Expected exception but none thrown");

            assertThat(thrown.getMessage(), is(equalTo(String.format("User %s not found", USER_ID))));
        }
    }
}
