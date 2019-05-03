package io.lishman.green;

import io.lishman.green.annotations.TestProfile;
import io.lishman.green.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Full stack test which connects to a test database.
 * The test database password is specified here.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestProfile
@TestPropertySource(properties = {"spring.datasource.password = etSKasftUR74hNQgwdhxbXH7m8LGG"})
class GreenApplicationWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Given the full application is running, when a get request on the users endpoint, then all users are retrieved")
    void givenTheFullApplicationIsRunningWhenAGetRequestOnTheUsersEndpointThenAllUsersAreRetrieved() throws Exception {
        this.webTestClient
                .get()
                .uri("/green/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class).hasSize(8).contains(firstUser(), lastUser());
    }

    private User firstUser() {
        return User.newInstance(
                16L,
                "Leanne",
                "Graham",
                "Bret",
                "Sincere@april.biz",
                "1-770-736-8031 x56442",
                17,
                "hildegard.org"
        );
    }


    private User lastUser() {
        return User.newInstance(
                23L,
                "Nicholas",
                "Runolfsdottir V",
                "Maxime_Nienow",
                "Sherwood@rosamond.me",
                "586.493.6943 x140",
                43,
                "jacynthe.com"
        );
    }

}
