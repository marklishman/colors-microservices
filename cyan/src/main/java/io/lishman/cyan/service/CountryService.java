package io.lishman.cyan.service;

import io.lishman.cyan.model.Country;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public final class CountryService {

    public List<Country> getCountries() {

        final WebClient webClient = WebClient
                .builder()
                .baseUrl("http://localhost:8021")
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient
                .get()
                .uri("green/countries")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Country.class)
                .collectList()
                .block();
    }

    public Country getCountry(final Long id) {

        final WebClient webClient = WebClient
                .builder()
                .baseUrl("http://localhost:8021")
                .build();

        return webClient
                .get()
                .uri("green/countries/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Country.class)
                .block();
    }

}
