package io.lishman.webflux.handler;

import io.lishman.webflux.model.User;
import io.lishman.webflux.model.UserEvent;
import io.lishman.webflux.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class UserHandler {

    private UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        Flux<User> users = userRepository.findAll();

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(users, User.class);
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<User> userMono = this.userRepository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return userMono
                .flatMap(user ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .body(fromObject(user)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> saveUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);

        return userMono.flatMap(User ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(userRepository.save(User), User.class));
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<User> existingUserMono = this.userRepository.findById(id);
        Mono<User> userMono = request.bodyToMono(User.class);

        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return userMono
                .zipWith(existingUserMono,
                        (user, existingUser) ->
                                new User(
                                        id,
                                        user.getName(),
                                        user.getEmail(),
                                        user.getPhone(),
                                        user.getUsername(),
                                        user.getWebsite()
                                )
                )
                .flatMap(user ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .body(userRepository.save(user), User.class)
                ).switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<User> userMono = this.userRepository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return userMono
                .flatMap(existingUser ->
                        ServerResponse.ok()
                                .build(userRepository.delete(existingUser))
                )
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteAllUsers(ServerRequest request) {
        return ServerResponse.ok()
                .build(userRepository.deleteAll());
    }

    public Mono<ServerResponse> getUserEvents(ServerRequest request) {
        Flux<UserEvent> eventsFlux = Flux.interval(Duration.ofSeconds(2)).map(val ->
                new UserEvent(val, "User Event " + val)
        );

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(eventsFlux, UserEvent.class);
    }
}
