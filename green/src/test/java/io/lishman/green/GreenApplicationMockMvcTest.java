package io.lishman.green;

import io.lishman.green.testing.annotations.TestProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Full stack with a test postgres database.
 * It is a test database so it is ok to include the password here.
 *
 * Mock MVC
 */

@SpringBootTest
@TestProfile
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.datasource.password = etSKasftUR74hNQgwdhxbXH7m8LGG"})
class GreenApplicationMockMvcTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Given the full application is running, when a get request on the /users endpoint, then all users are retrieved")
    void givenTheFullApplicationIsRunningWhenAGetRequestOnTheUsersEndpointThenAllUsersAreRetrieved() throws Exception {

        this.mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(is(equalTo(8))))
                .andExpect(content().string(containsString("\"fullName\":\"Leanne Graham\"")))
                .andExpect(content().string(containsString("\"fullName\":\"Nicholas Runolfsdottir V\"")));
    }
}
