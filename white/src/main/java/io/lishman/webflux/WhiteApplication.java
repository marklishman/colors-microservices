package io.lishman.webflux;

import io.lishman.webflux.handler.UserHandler;
import io.lishman.webflux.model.User;
import io.lishman.webflux.repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class WhiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhiteApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepo repo) {
        return args -> {
            Flux<User> userFlux = Flux.just(
                new User("one", "user_one", "one@email.com", "01234567", "www.one.com"),
                new User("two", "user_two", "two@email.com", "056754333", "www.two.com"),
                new User("three", "user_three", "three@email.com", "067856469", "www.three.com")
            ).flatMap(repo::save);

            userFlux.thenMany(repo.findAll())
                    .subscribe(System.out::println);
        };
    }

    @Bean
    @Profile("!ControllerTest")
    RouterFunction<ServerResponse> routes(UserHandler handler) {
        return route(GET("/users").and(accept(APPLICATION_JSON)), handler::getAllUsers)
				.andRoute(POST("/users").and(contentType(APPLICATION_JSON)), handler::saveUser)
				.andRoute(DELETE("/users").and(accept(APPLICATION_JSON)), handler::deleteAllUsers)
				.andRoute(GET("/users/events").and(accept(TEXT_EVENT_STREAM)), handler::getUserEvents)
				.andRoute(GET("/users/{id}").and(accept(APPLICATION_JSON)), handler::getUser)
				.andRoute(PUT("/users/{id}").and(contentType(APPLICATION_JSON)), handler::updateUser)
				.andRoute(DELETE("/users/{id}").and(accept(APPLICATION_JSON)), handler::deleteUser);


//        return nest(path("/handler/users"),
//                nest(accept(APPLICATION_JSON).or(contentType(APPLICATION_JSON)).or(accept(TEXT_EVENT_STREAM)),
//                        route(GET("/"), handler::getAllUsers)
//                                .andRoute(method(HttpMethod.POST), handler::saveUser)
//                                .andRoute(DELETE("/"), handler::deleteAllUsers)
//                                .andRoute(GET("/events"), handler::getUserEvents)
//                                .andNest(path("/{id}"),
//                                        route(method(HttpMethod.GET), handler::getUser)
//                                                .andRoute(method(HttpMethod.PUT), handler::updateUser)
//                                                .andRoute(method(HttpMethod.DELETE), handler::deleteUser)
//                                )
//                )
//        );
    }


}
