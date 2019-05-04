package io.lishman.green;

import io.lishman.green.testing.annotations.TestProfile;
import io.lishman.green.testing.fixtures.UserFixture;
import io.lishman.green.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Full stack with a test postgres database.
 * It is a test database so it is ok to include the password here.
 *
 * WebTestClient
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestProfile
@TestPropertySource(properties = {"spring.datasource.password = etSKasftUR74hNQgwdhxbXH7m8LGG"})
class GreenApplicationWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Given the full application is running, when a get request on the /users endpoint, then all users are retrieved")
    void givenTheFullApplicationIsRunningWhenAGetRequestOnTheUsersEndpointThenAllUsersAreRetrieved() {

        this.webTestClient
                .get()
                .uri("/green/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class).hasSize(8).contains(UserFixture.leanneGraham(), UserFixture.nicholasRunolfsdottir());
    }

}
