package io.lishman.cyan.service;

import io.lishman.cyan.model.Country;
import io.lishman.cyan.model.Person;
import io.lishman.cyan.model.Statistics;
import io.lishman.cyan.model.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public final class StatisticsService {

    public Statistics getStats() {

        final WebClient greenClient = WebClient
                .builder()
                .baseUrl("http://localhost:8021")
                .build();

        final Mono<Long> countryCountMono = greenClient
                .get()
                .uri("green/countries")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Country.class)
                .count();

        final Mono<Long> peopleCountMono = greenClient
                .get()
                .uri("green/people")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Person.class)
                .count();


        final WebClient whiteClient = WebClient.create("http://localhost:8071");

        final Mono<Long> userCountMono = whiteClient
                .get()
                .uri("controller/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class)
                .count();

        return Flux.concat(countryCountMono, peopleCountMono, userCountMono)
                .collectList()
                .map(list -> Statistics.newInstance(list.get(0), list.get(1), list.get(2)))
                .block();
    }

}
