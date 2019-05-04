package io.lishman.green;

import io.lishman.green.exception.UserResourceNotFoundException;
import io.lishman.green.model.User;
import io.lishman.green.service.UserService;
import io.lishman.green.testing.annotations.DisableJpa;
import io.lishman.green.testing.config.ServiceMocks;
import io.lishman.green.testing.fixtures.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.BDDMockito.given;

/**
 * WebTestClient with mock services.
 */

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ServiceMocks.class
)
@DisableJpa
class GreenApplicationWebClientTest {

    private static final Long USER_ID = 16L;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Given the full application is running, " +
            "when a get request on the /users endpoint, " +
            "then all users are retrieved")
    void getUsers() {

        given(userService.getAllUsers()).willReturn(UserFixture.users());

        this.webTestClient
                .get()
                .uri("/green/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class).hasSize(2).contains(UserFixture.leanneGraham(), UserFixture.nicholasRunolfsdottir());
    }

    @Test
    @DisplayName("Given user does not exist, " +
            "when get request on the /users/{id} endpoint, " +
            "then 404 status is returned")
    void getUserByIdNotFound() throws Exception {

        given(userService.getUserById(USER_ID)).willThrow(new UserResourceNotFoundException(USER_ID));

        this.webTestClient
                .get()
                .uri("/green/users/{id}", USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().jsonPath("$.message").isEqualTo(String.format("User %s not found", USER_ID));
    }
}
