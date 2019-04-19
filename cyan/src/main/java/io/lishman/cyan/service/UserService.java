package io.lishman.cyan.service;

import io.lishman.cyan.model.User;
import io.lishman.cyan.model.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

/**
 *  WebClient with WebFlux
 */

@Service
public final class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final WebClient whiteWebClient;

    private UserEvent latestUserEvent;

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

    public UserEvent getLatestUserEvent() {
        return latestUserEvent;
    }

    @PostConstruct
    public void userEventStream() {
        LOGGER.info("Starting User Event Stream");

        whiteWebClient
                .get()
                .uri("/controller/users/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(UserEvent.class)
                .log()
                .subscribe(userEvent -> latestUserEvent = userEvent);
    }

}
