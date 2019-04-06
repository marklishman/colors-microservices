package io.lishman.cyan.webflux;

import io.lishman.cyan.model.User;
import io.lishman.cyan.model.UserEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebClientAPI {

    private WebClient webClient;

    WebClientAPI() {
        //this.webClient = WebClient.create("http://localhost:8071/users");
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8071/controller/users")
                .build();
    }

    public static void main(String args[]) {
        WebClientAPI api = new WebClientAPI();

        api.postNewUser()
                .thenMany(api.getAllUsers())
                .take(1)
                .flatMap(user -> api.updateUser(user.getUserId(), "Updated User Name"))
                .flatMap(user -> api.deleteUser(user.getUserId()))
                .thenMany(api.getAllUsers())
                .thenMany(api.getAllEvents())
                .subscribe(System.out::println);
    }

    private Mono<ResponseEntity<User>> postNewUser() {
        return webClient
                .post()
                .body(Mono.just(new User("four", "user_four", "four@email.com", "067856469", "www.four.com")), User.class)
                .exchange()
                .flatMap(response -> response.toEntity(User.class))
                .doOnSuccess(o -> System.out.println("**********POST " + o));
    }

    private Flux<User> getAllUsers() {
        return webClient
                .get()
                .retrieve()
                .bodyToFlux(User.class)
                .doOnNext(o -> System.out.println("**********GET: " + o));
    }

    private Mono<User> updateUser(String id, String name) {
        return webClient
                .put()
                .uri("/{id}", id)
                .body(Mono.just(new User(name, "user_four", "four@email.com", "067856469", "www.four.com")), User.class)
                .retrieve()
                .bodyToMono(User.class)
                .doOnSuccess(o -> System.out.println("**********UPDATE " + o));
    }

    private Mono<Void> deleteUser(String id) {
        return webClient
                .delete()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(o -> System.out.println("**********DELETE " + o));
    }

    private Flux<UserEvent> getAllEvents() {
        return webClient
                .get()
                .uri("/events")
                .retrieve()
                .bodyToFlux(UserEvent.class);
    }
}
