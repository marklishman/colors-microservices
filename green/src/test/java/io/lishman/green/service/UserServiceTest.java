package io.lishman.green.service;

import io.lishman.green.entity.UserEntity;
import io.lishman.green.model.User;
import io.lishman.green.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

class UserServiceTest {

    private static final long USER_ID = 10L;

    @Nested
    @IntegrationTest
    @DisplayName("getAllUsers() method")
    class GetAllUsers {

        @Autowired
        private UserService userService;

        @Test
        @DisplayName("Given there are no users, then an empty list is returned")
        void givenThereAreNoUsersThenAnEmptyListIsReturned() {
            final List<User> actual = userService.getAllUsers();
            assertThat(actual, hasSize(0));
        }

        @Test
        @DisplayName("Given there is one user, then a list with one is returned")
        void givenThereAreUsersThenListWithOneUserIsReturned(@Autowired final UserRepository userRepository) {
            final List<UserEntity> userEntities = Collections.singletonList(userEntity());
            given(userRepository.findAll()).willReturn(userEntities);
            final List<User> actual = userService.getAllUsers();
            assertThat(actual, hasSize(1));
        }
    }

    @Nested
    @IntegrationTest
    @DisplayName("getAllUserById(Long) method")
    class GetUserBy {

        @Autowired
        private UserService userService;

        @Test
        @DisplayName("Given there is a user with the id, the correct user is returned")
        void givenThereAreUsersThenListWithOneUserIsReturned(@Autowired final UserRepository userRepository) {
            given(userRepository.findById(USER_ID)).willReturn(Optional.of(userEntity()));
            final User actual = userService.getUserById(USER_ID);
            assertThat(actual.getUserName(), is(equalTo("user.me")));
        }
    }

    // TODO Use Fixtures
    private UserEntity userEntity() {
        return UserEntity.fromUser(user());
    }

    private User user() {
        return User.newInstance(
                123L,
                "Bob",
                "Smith",
                "user.me",
                "abc@email.com",
                "01772 776453",
                25,
                "www.example.com"
        );
    }
}
