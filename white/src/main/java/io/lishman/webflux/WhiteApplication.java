package io.lishman.webflux;

import io.lishman.webflux.handler.UserHandler;
import io.lishman.webflux.model.User;
import io.lishman.webflux.repository.UserRepository;
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
    CommandLineRunner init(UserRepository repo) {
        return args -> {
            Flux<User> userFlux = Flux.just(
                new User("one", "user_one", "one@email.com", "012345672", "www.one.com"),
                new User("two", "user_two", "two@email.com", "056543336", "www.two.com"),
                new User("three", "user_three", "three@email.com", "048745663", "www.three.com"),
                new User("four", "user_four", "four@email.com", "087645767", "www.four.com"),
                new User("five", "user_five", "five@email.com", "087686324", "www.five.com"),
                new User("six", "user_six", "six@email.com", "074657634", "www.six.com"),
                new User("seven", "user_seven", "seven@email.com", "083647566", "www.seven.com"),
                new User("eight", "user_eight", "eight@email.com", "073465763", "www.eight.com"),
                new User("nine", "user_nine", "nine@email.com", "043756236", "www.nine.com")
            ).flatMap(repo::save);

            userFlux.thenMany(repo.findAll())
                    .subscribe(System.out::println);
        };
    }

    @Bean
    @Profile("!ControllerTest")
    RouterFunction<ServerResponse> routes(UserHandler handler) {

        // ~~~~ Flat
        return route(GET("/handler/users").and(accept(APPLICATION_JSON)), handler::getAllUsers)
				.andRoute(POST("/handler/users").and(contentType(APPLICATION_JSON)), handler::saveUser)
				.andRoute(DELETE("/handler/users").and(accept(APPLICATION_JSON)), handler::deleteAllUsers)
				.andRoute(GET("/handler/users/events").and(accept(TEXT_EVENT_STREAM)), handler::getUserEvents)
				.andRoute(GET("/handler/users/{id}").and(accept(APPLICATION_JSON)), handler::getUser)
				.andRoute(PUT("/handler/users/{id}").and(contentType(APPLICATION_JSON)), handler::updateUser)
				.andRoute(DELETE("/handler/users/{id}").and(accept(APPLICATION_JSON)), handler::deleteUser);

        // ~~~~ Nested
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
