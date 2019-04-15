package io.lishman.cyan.service;

import io.lishman.cyan.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 *  WebClient with WebFlux
 */

@Service
public final class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final WebClient whiteWebClient;

    public UserService(WebClient whiteWebClient) {
        this.whiteWebClient = whiteWebClient;
    }

    public ResponseEntity<Flux<User>> getUsers() {
        LOGGER.info("Get Users");

        final Flux<User> users = whiteWebClient
                .get()
                .uri("/controller/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);

        return ResponseEntity.ok(users);
    }

}
