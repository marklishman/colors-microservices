package io.lishman.cyan.webflux;

import io.lishman.cyan.model.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class RemoteUserController {

    private WebClient client = WebClient.create("https://jsonplaceholder.typicode.com");

    @GetMapping("/remote/user/{id}")
    public User getUser(@PathVariable("id") final Integer id) {

        Mono<ClientResponse> result = client.get()
                .uri("/users/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        return result
                .flatMap(res -> res.bodyToMono(User.class))
                .block();
    }

}
