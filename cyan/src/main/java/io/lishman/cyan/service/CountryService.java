package io.lishman.cyan.service;

import io.lishman.cyan.model.Country;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public final class CountryService {

    private final WebClient greenWebClient;

    public CountryService(final WebClient greenWebClient) {
        this.greenWebClient = greenWebClient;
    }

    public List<Country> getCountries() {
        return greenWebClient
                .get()
                .uri("green/countries")
                .retrieve()
                .bodyToFlux(Country.class)
                .collectList()
                .block();
    }

    public Country getCountry(final Long id) {
        return greenWebClient
                .get()
                .uri("green/countries/{id}", id)
                .retrieve()
                .bodyToMono(Country.class)
                .block();
    }

}
