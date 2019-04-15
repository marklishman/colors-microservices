package io.lishman.cyan.service;

import io.lishman.cyan.model.User;
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

    public ResponseEntity<Flux<User>> getUsers() {

        final WebClient webClient = WebClient.create("http://localhost:8071");

        final Flux<User> users = webClient
                .get()
                .uri("/controller/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);

        return ResponseEntity.ok(users);
    }

}
