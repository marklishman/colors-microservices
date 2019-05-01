package io.lishman.green.service;

import io.lishman.green.entity.UserEntity;
import io.lishman.green.model.User;
import io.lishman.green.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

// TODO use custom annotation here
@SpringJUnitConfig(classes = TestConfig.class)
class UserServiceTest {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Nested
    @DisplayName("getAllUsers() method")
    class GetAllUsers {

        @Test
        @DisplayName("Given there are no users, then an empty list is returned")
        void givenThereAreNoUsersThenAnEmptyListIsReturned() {
            final List<User> actual = userService.getAllUsers();
            assertThat(actual, hasSize(0));
        }

        @Test
        @DisplayName("Given there is one user, then a list with one is returned")
        void givenThereAreUsersThenListWithOneUserIsReturned() {
            final List<UserEntity> userEntities = Collections.singletonList(userEntity());
            given(userRepository.findAll()).willReturn(userEntities);
            final List<User> actual = userService.getAllUsers();
            assertThat(actual, hasSize(1));
        }
    }

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
