package io.lishman.cyan.service;

import io.lishman.cyan.model.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public final class CountryService {

    public ResponseEntity<Flux<User>> getCountries() {

        final WebClient webClient = WebClient.create("http://localhost:8021");

        final Flux<User> users = webClient
                .get()
                .uri("green/countries")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);

        return ResponseEntity.ok(users);
    }

}
