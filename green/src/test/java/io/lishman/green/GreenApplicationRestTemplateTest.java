package io.lishman.green;

import io.lishman.green.annotations.TestProfile;
import io.lishman.green.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

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
    void givenTheFullApplicationIsRunningWhenAGetRequestOnTheUsersEndpointThenAllUsersAreRetrieved() {

        System.out.println("Running on port " + port);

        final ResponseEntity<List<User>> response = this.restTemplate.exchange(
                "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>(){}
        );

        assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
        final List<User> actualUsers = response.getBody();
        assertThat(actualUsers, hasSize(8));
        assertThat(actualUsers.get(0), is(equalTo(firstUser())));
        assertThat(actualUsers.get(7), is(equalTo(lastUser())));
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
