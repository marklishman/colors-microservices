package io.lishman.green;

import io.lishman.green.annotations.TestProfile;
import io.lishman.green.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

/**
 * Full stack test which connects to a test database.
 * The test database password is specified here.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestProfile
@TestPropertySource(properties = {"spring.datasource.password = etSKasftUR74hNQgwdhxbXH7m8LGG"})
class GreenApplicationRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Given the full application is running, when a get request on the users endpoint, then all users are retrieved")
    void givenTheFullApplicationIsRunningWhenAGetRequestOnTheUsersEndpointThenAllUsersAreRetrieved() throws Exception {
        System.out.println("Running on port " + port);
        String body = this.restTemplate.getForObject("/users", String.class);

        System.out.println(body);
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
