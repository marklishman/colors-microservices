package io.lishman.cyan.service;

import io.lishman.cyan.model.Country;
import io.lishman.cyan.model.Person;
import io.lishman.cyan.model.Statistics;
import io.lishman.cyan.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *  WebClient with merge
 */

@Service
public final class StatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsService.class);

    private final WebClient greenWebClient;
    private final WebClient whiteWebClient;

    public StatisticsService(final WebClient greenWebClient, final WebClient whiteWebClient) {
        this.greenWebClient = greenWebClient;
        this.whiteWebClient = whiteWebClient;
    }

    public Statistics getStats() {

        final Mono<Long> peopleCountMono = greenWebClient
                .get()
                .uri("green/people")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Person.class)
                .count();

        final Mono<Long> countryCountMono = greenWebClient
                .get()
                .uri("green/countries")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Country.class)
                .count();

        final Mono<Long> userCountMono = whiteWebClient
                .get()
                .uri("controller/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class)
                .count();

        return Flux.mergeSequential(peopleCountMono, countryCountMono, userCountMono)
                .collectList()
                .map(list -> Statistics.newInstance(list.get(0), list.get(1), list.get(2)))
                .block();
    }

}
