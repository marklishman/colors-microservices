package io.lishman.cyan.service;

import io.lishman.cyan.model.User;
import io.lishman.cyan.model.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public final class WebClientUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientUserService.class);

    private final WebClient whiteWebClient;
    private final WebClientUserWebFluxClient webClientUserWebFluxClient;

    private Disposable disposable;
    private UserEvent latestUserEvent;

    public WebClientUserService(WebClient whiteWebClient, final WebClientUserWebFluxClient webClientUserWebFluxClient) {
        this.whiteWebClient = whiteWebClient;
        this.webClientUserWebFluxClient = webClientUserWebFluxClient;
    }

    public ResponseEntity<Flux<User>> getUsers() {
        LOGGER.info("Get Users with WebClient from WebFlux");
        final Flux<User> users = whiteWebClient
                .get()
                .uri("/controller/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);

        return ResponseEntity.ok(users);
    }

    public ResponseEntity<Mono<User>> getUser(final String id) {
        LOGGER.info("Get User {} with WebClient from WebFlux", id);
        final Mono<User> user = whiteWebClient
                .get()
                .uri("/controller/users/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class);

        return ResponseEntity.ok(user);
    }

    public void webFluxClient() {
        LOGGER.info("Multiple calls to WebFux API with WebClient");
        webClientUserWebFluxClient.runClient();
    }

    public String toggleUserEventStream() {
        return this.disposable == null ?
                startUserEventStream() :
                stopUserEventStream();
    }

    public UserEvent getLatestUserEvent() {
        return latestUserEvent;
    }

    private String startUserEventStream() {
        LOGGER.info("Starting User Event Stream");
        disposable = whiteWebClient
                .get()
                .uri("/controller/users/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(UserEvent.class)
                .log()
                .subscribe(userEvent -> latestUserEvent = userEvent);
        return "started";
    }

    private String stopUserEventStream() {
        LOGGER.info("Stopping User Event Stream");
        disposable.dispose();
        disposable = null;
        return "stopped";
    }

}
