package io.lishman.cyan.service;

import io.lishman.cyan.model.Employee;
import io.lishman.cyan.model.Statistics;
import io.lishman.cyan.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Service
public final class WebClientStatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientStatisticsService.class);

    private final WebClient greenWebClient;
    private final WebClient whiteWebClient;
    private final WebClient purpleHalWebClient;

    public WebClientStatisticsService(final WebClient greenWebClient,
                                      final WebClient whiteWebClient,
                                      final WebClient purpleHalWebClient) {
        this.greenWebClient = greenWebClient;
        this.whiteWebClient = whiteWebClient;
        this.purpleHalWebClient = purpleHalWebClient;
    }

    public Statistics getStats() {
        LOGGER.info("Get Statistics with WebClient");

        final Mono<Long> userCountMono = greenWebClient
                .get()
                .uri("green/users")
                .retrieve()
                .bodyToFlux(User.class)
                .count();

        final Mono<Long> countryCountMono = purpleHalWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("purple/api/countries")
                        .queryParam("size", Integer.MAX_VALUE)
                        .build()
                )
                .retrieve()
                .bodyToMono(Resources.class)
                .map(Resources::getContent)
                .map(Collection::size)
                .map(Long::valueOf);

        final Mono<Long> employeeCountMono = whiteWebClient
                .get()
                .uri("controller/employees")
                .retrieve()
                .bodyToFlux(Employee.class)
                .count();

        return Flux.mergeSequential(userCountMono, countryCountMono, employeeCountMono)
                .collectList()
                .map(list -> Statistics.newInstance(list.get(0), list.get(1), list.get(2)))
                .block();
    }

}
