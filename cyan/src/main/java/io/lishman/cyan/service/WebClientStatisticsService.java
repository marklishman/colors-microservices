package io.lishman.cyan.service;

import io.lishman.cyan.model.Country;
import io.lishman.cyan.model.Person;
import io.lishman.cyan.model.Statistics;
import io.lishman.cyan.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public final class WebClientStatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientStatisticsService.class);

    private final WebClient greenWebClient;
    private final WebClient whiteWebClient;

    public WebClientStatisticsService(final WebClient greenWebClient, final WebClient whiteWebClient) {
        this.greenWebClient = greenWebClient;
        this.whiteWebClient = whiteWebClient;
    }

    public Statistics getStats() {
        LOGGER.info("Get Statistics with WebClient");

        final Mono<Long> peopleCountMono = greenWebClient
                .get()
                .uri("green/people")
                .retrieve()
                .bodyToFlux(Person.class)
                .count();

        final Mono<Long> countryCountMono = greenWebClient
                .get()
                .uri("green/countries")
                .retrieve()
                .bodyToFlux(Country.class)
                .count();

        final Mono<Long> userCountMono = whiteWebClient
                .get()
                .uri("controller/users")
                .retrieve()
                .bodyToFlux(User.class)
                .count();

        return Flux.mergeSequential(peopleCountMono, countryCountMono, userCountMono)
                .collectList()
                .map(list -> Statistics.newInstance(list.get(0), list.get(1), list.get(2)))
                .block();
    }

}
