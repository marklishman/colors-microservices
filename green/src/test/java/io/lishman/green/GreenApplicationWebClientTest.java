package io.lishman.green;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.datasource.password = etSKasftUR74hNQgwdhxbXH7m8LGG"})
class GreenApplicationWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Disabled("WebTestClient returns 404")
    public void testExample() {
        this.webTestClient
                .get()
                .uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

}
