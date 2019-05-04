package io.lishman.green.controller.user;

import io.lishman.green.service.UserService;
import io.lishman.green.testing.config.ServiceMocks;
import io.lishman.green.testing.fixtures.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(ServiceMocks.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Given the application with mock services, when a get request on the /users endpoint, then all users are retrieved")
    void givenTheApplicationWithMockServicesWhenAGetRequestOnTheUsersEndpointThenAllUsersAreRetrieved() throws Exception {

        given(userService.getAllUsers()).willReturn(UserFixture.users());

        this.mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(is(equalTo(2))))
                .andExpect(jsonPath("$[0].fullName").value(is(equalTo("Leanne Graham"))))
                .andExpect(jsonPath("$[1].fullName").value(is(equalTo("Nicholas Runolfsdottir V"))));
    }
}
