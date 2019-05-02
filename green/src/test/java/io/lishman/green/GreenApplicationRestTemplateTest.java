package io.lishman.green;

import io.lishman.green.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.datasource.password = etSKasftUR74hNQgwdhxbXH7m8LGG"})
class GreenApplicationRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void exampleTest() {
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
