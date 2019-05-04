package io.lishman.green;

import io.lishman.green.model.User;
import io.lishman.green.testing.annotations.TestProfile;
import io.lishman.green.testing.fixtures.UserFixture;
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
 * Full stack with a test postgres database.
 * It is a test database so it is ok to include the password here.
 *
 * TestRestTemplate
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestProfile
@TestPropertySource(properties = {"spring.datasource.password = etSKasftUR74hNQgwdhxbXH7m8LGG"})
class GreenApplicationRestTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Given the full application is running, " +
            "when a get request on the /users endpoint, " +
            "then all users are retrieved")
    void getUsers() {

        System.out.println("Running on port " + port);

        final ResponseEntity<List<User>> response = this.testRestTemplate.exchange(
                "/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>(){}
        );

        assertThat(response.getStatusCode().is2xxSuccessful(), is(true));
        final List<User> actualUsers = response.getBody();
        assertThat(actualUsers, hasSize(8));
        assertThat(actualUsers.get(0), is(equalTo(UserFixture.leanneGraham())));
        assertThat(actualUsers.get(7), is(equalTo(UserFixture.nicholasRunolfsdottir())));
    }
}
