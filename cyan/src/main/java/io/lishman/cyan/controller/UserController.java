package io.lishman.cyan.controller;

import io.lishman.cyan.model.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/users")
final class UserController {

    @GetMapping
    ResponseEntity<Flux<User>> getUsers() {

        final WebClient webClient = WebClient.create("http://localhost:8071");

        Flux<User> users = webClient
                .get()
                .uri("controller/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);

        return ResponseEntity.ok(users);
    }

}
