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
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    @DisplayName("GET /users")
    class GetUsers {

        @Test
        @DisplayName("Given no users exist, " +
                "when there is a GET request on the /users endpoint, " +
                "then an empty list is returned")
        void emptyListOfUsers() throws Exception {

            given(userService.getAllUsers()).willReturn(Collections.emptyList());

            mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(jsonPath("$.length()").value(is(equalTo(0))));
        }

        @Test
        @DisplayName("Given some users exist, " +
                "when there is a GET request on the /users endpoint, " +
                "then all users are retrieved")
        void listOfUsers() throws Exception {

            given(userService.getAllUsers()).willReturn(UserFixture.users());

            mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(jsonPath("$.length()").value(is(equalTo(2))))
                    .andExpect(jsonPath("$[0].fullName").value(is(equalTo("Leanne Graham"))))
                    .andExpect(jsonPath("$[1].fullName").value(is(equalTo("Nicholas Runolfsdottir V"))));
        }

        @Test
        @DisplayName("Given some users exist, " +
                "when there is a GET request on the /users endpoint " +
                "and the HAL media type is specified in the Accept header, " +
                "then all users are retrieved " +
                "and the contents are in HAL format")
        void listOfUsersInHal() throws Exception {

            given(userService.getAllUsers()).willReturn(UserFixture.users());

            mvc.perform(get("/users").accept(MediaTypes.HAL_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                    .andExpect(jsonPath("$._embedded.users.length()").value(is(equalTo(2))))
                    .andExpect(jsonPath("$._embedded.users[0].lastName").value(is(equalTo("Graham"))))
                    .andExpect(jsonPath("$._embedded.users[1].lastName").value(is(equalTo("Runolfsdottir V"))));
        }
    }

    @Nested
    @DisplayName("GET /users/{id}")
    class GetUsersWithId {

        @Test
        @DisplayName("Given a user exists, " +
                "when there is a GET request on the /users/{id} endpoint, " +
                "then the correct user is retrieved")
        void userIsFoundById() throws Exception {

            given(userService.getUserById(USER_ID)).willReturn(UserFixture.leanneGraham());

            mvc.perform(get("/users/{id}", USER_ID).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(jsonPath("$.fullName").value(is(equalTo("Leanne Graham"))));
        }

        @Test
        @DisplayName("Given a user exists, " +
                "when there is a GET request on the /users/{id} endpoint, " +
                "and the HAL media type is specified in the Accept header, " +
                "then the correct user is retrieved " +
                "and the contents are in HAL format")
        void userIsFoundByIdAsHal() throws Exception {

            given(userService.getUserById(USER_ID)).willReturn(UserFixture.leanneGraham());

            mvc.perform(get("/users/{id}", USER_ID).accept(MediaTypes.HAL_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                    .andDo(print())
                    .andExpect(jsonPath("$.lastName").value(is(equalTo("Graham"))))
                    .andExpect(jsonPath("$._links.self.href").value(is(equalTo("http://localhost/users/1"))));
        }

        @Test
        @DisplayName("Given a user does not exist, " +
                "when there is a GET request on the /users/{id} endpoint with a missing id, " +
                "then a 404 status is returned")
        void userNotFoundById() throws Exception {

            given(userService.getUserById(USER_ID)).willThrow(new UserResourceNotFoundException(USER_ID));

            mvc.perform(get("/users/{id}", USER_ID).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(404));
        }
    }

    @Nested
    @DisplayName("POST /users")
    class PostUsers {

        @Test
        @DisplayName("Given a new user is to be added, " +
                "when a POST is made to the /users endpoint, " +
                "then the new user is created " +
                "and the HTTP status is 201 " +
                "and the user details are returned with the new id")
        void createUser() throws Exception {

            given(userService.createUser(UserFixture.bobSmithWithNullId()))
                    .willReturn(UserFixture.bobSmith());

            mvc.perform(
                    post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(UserFixture.bobSmithJson()))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(is(equalTo(6))))
                    .andExpect(jsonPath("$.fullName").value(is(equalTo("Bob Smith"))));
        }
    }

    @Nested
    @DisplayName("PUT /users/{id}")
    class PutUsers {

        @Test
        @DisplayName("Given a user exists, " +
                "when a PUT is made to the /users/{id} endpoint with a that users id, " +
                "then the user is updated with the values in the request body " +
                "and an empty response body is returned " +
                "and the HTTP status is 204")
        void updateUser() throws Exception {

            mvc.perform(
                    put("/users/{id}", USER_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(UserFixture.bobSmithJson()))
                    .andExpect(status().isNoContent())
                    .andExpect(content().string(""));

            verify(userService).updateUser(USER_ID, UserFixture.bobSmithWithNullId());
        }

        @Test
        @DisplayName("Given a user does not exist, " +
                "when a PUT is made to the /users/{id} endpoint with a bad id, " +
                "then an empty response body is returned (this is handled by the servlet container) " +
                "and the HTTP status is 404")
        void updateUserNotFound() throws Exception {

            given(userService.updateUser(USER_ID, UserFixture.bobSmithWithNullId()))
                    .willThrow(new UserResourceNotFoundException(USER_ID));

            mvc.perform(
                    put("/users/{id}", USER_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(UserFixture.bobSmithJson()))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(""));
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id}")
    class DeleteUsers {

        @Test
        @DisplayName("Given a user exists, " +
                "when a DELETE is made to the /users{id} endpoint with a that users id, " +
                "then the user is deleted " +
                "and an empty response body is returned " +
                "and the HTTP status is 204")
        void updateUser() throws Exception {

            mvc.perform(
                    delete("/users/{id}", USER_ID)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andExpect(content().string(""));

            verify(userService).deleteUser(USER_ID);
        }

        @Test
        @DisplayName("Given a user does not exist, " +
                "when a DELETE is made to the /users/{id} endpoint with a bad id, " +
                "then an empty response body is returned (this is handled by the servlet container) " +
                "and the HTTP status is 404")
        void updateUserNotFound() throws Exception {

            doThrow(UserResourceNotFoundException.class).when(userService).deleteUser(USER_ID);

            mvc.perform(
                    delete("/users/{id}", USER_ID)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string(""));
        }
    }
}
