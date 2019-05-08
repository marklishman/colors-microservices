package io.lishman.green.controller.user;

import io.lishman.green.exception.UserResourceNotFoundException;
import io.lishman.green.service.UserService;
import io.lishman.green.testing.config.ServiceMocks;
import io.lishman.green.testing.fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("User Controller Integration Tests")
@Import(ServiceMocks.class)
class UserControllerTest {

    private static final Long USER_ID = 1L;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @BeforeEach
    void beforeEach() {
        Mockito.reset(userService);
    }

    @Nested
    @DisplayName("/users endpoint")
    class UsersEndpoint {

        @Test
        @DisplayName("Given no users exist, " +
                "when there is a get request on the /users endpoint, " +
                "then empty list is returned")
        void noUsersInList() throws Exception {

            given(userService.getAllUsers()).willReturn(Collections.emptyList());

            mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(is(equalTo(0))));
        }

        @Test
        @DisplayName("Given some users exist, " +
                "when there is a get request on the /users endpoint, " +
                "then all users are retrieved")
        void userInList() throws Exception {

            given(userService.getAllUsers()).willReturn(UserFixture.users());

            mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(is(equalTo(2))))
                    .andExpect(jsonPath("$[0].fullName").value(is(equalTo("Leanne Graham"))))
                    .andExpect(jsonPath("$[1].fullName").value(is(equalTo("Nicholas Runolfsdottir V"))));
        }
    }

    @Nested
    @DisplayName("/users/{id} endpoint")
    class UsersIdEndpoint {

        @Test
        @DisplayName("Given user does not exist, " +
                "when there is a get request on the /users/{id} endpoint, " +
                "then 404 status is returned")
        void userNotFoundById() throws Exception {

            given(userService.getUserById(USER_ID)).willThrow(new UserResourceNotFoundException(USER_ID));

            mvc.perform(get("/users/{id}", USER_ID).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(404));
        }

        @Test
        @DisplayName("Given a user exists, " +
                "when get request on the /users/{id} endpoint, " +
                "then the correct user is retrieved")
        void userIsFoundById() throws Exception {

            given(userService.getUserById(USER_ID)).willReturn(UserFixture.leanneGraham());

            mvc.perform(get("/users/{id}", USER_ID).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.fullName").value(is(equalTo("Leanne Graham"))));
        }
    }

}

