package io.lishman.webflux.handler;

import io.lishman.webflux.model.User;
import io.lishman.webflux.model.UserEvent;
import io.lishman.webflux.repository.UserRepo;
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

    private UserRepo userRepo;

    public UserHandler(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        Flux<User> users = userRepo.findAll();

        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(users, User.class);
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<User> userMono = this.userRepo.findById(id);
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
                        .body(userRepo.save(User), User.class));
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<User> existingUserMono = this.userRepo.findById(id);
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
                                .body(userRepo.save(user), User.class)
                ).switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<User> userMono = this.userRepo.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return userMono
                .flatMap(existingUser ->
                        ServerResponse.ok()
                                .build(userRepo.delete(existingUser))
                )
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteAllUsers(ServerRequest request) {
        return ServerResponse.ok()
                .build(userRepo.deleteAll());
    }

    public Mono<ServerResponse> getUserEvents(ServerRequest request) {
        Flux<UserEvent> eventsFlux = Flux.interval(Duration.ofSeconds(1)).map(val ->
                new UserEvent(val, "User Event")
        );

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(eventsFlux, UserEvent.class);
    }
}
