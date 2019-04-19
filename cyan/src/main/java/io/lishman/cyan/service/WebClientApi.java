package io.lishman.cyan.service;

import io.lishman.cyan.model.User;
import io.lishman.cyan.model.UserEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class WebClientApi {

    private final WebClient whiteWebClient;

    WebClientApi(WebClient whiteWebClient) {
        this.whiteWebClient = whiteWebClient;
    }

    public void runClient() {
        postNewUser()
                .thenMany(getAllUsers())
                .take(1)
                .flatMap(user -> updateUser(user.getUserId(), "Updated User Name"))
                .flatMap(user -> deleteUser(user.getUserId()))
                .thenMany(getAllUsers())
                .thenMany(getAllEvents())
                .subscribe(System.out::println);
    }

    private Mono<ResponseEntity<User>> postNewUser() {
        return whiteWebClient
                .post()
                .uri("/controller/users")
                .body(Mono.just(new User("four", "user_four", "four@email.com", "067856469", "www.four.com")), User.class)
                .exchange()
                .flatMap(response -> response.toEntity(User.class))
                .doOnSuccess(o -> System.out.println("**********POST " + o));
    }

    private Flux<User> getAllUsers() {
        return whiteWebClient
                .get()
                .uri("/controller/users")
                .retrieve()
                .bodyToFlux(User.class)
                .doOnNext(o -> System.out.println("**********GET: " + o));
    }

    private Mono<User> updateUser(String id, String name) {
        return whiteWebClient
                .put()
                .uri("/controller/users/{id}", id)
                .body(Mono.just(new User(name, "user_four", "four@email.com", "067856469", "www.four.com")), User.class)
                .retrieve()
                .bodyToMono(User.class)
                .doOnSuccess(o -> System.out.println("**********UPDATE " + o));
    }

    private Mono<Void> deleteUser(String id) {
        return whiteWebClient
                .delete()
                .uri("/controller/users/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(o -> System.out.println("**********DELETE " + o));
    }

    private Flux<UserEvent> getAllEvents() {
        return whiteWebClient
                .get()
                .uri("/controller/users/events")
                .retrieve()
                .bodyToFlux(UserEvent.class);
    }
}
