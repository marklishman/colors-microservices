package io.lishman.cyan.service;

import io.lishman.cyan.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  WebClient - synchronous
 */

@Service
public final class CountriesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountriesService.class);

    private final WebClient greenWebClient;
    private final WebClient greenHalWebClient;

    public CountriesService(final WebClient greenWebClient, final WebClient greenHalWebClient) {
        this.greenWebClient = greenWebClient;
        this.greenHalWebClient = greenHalWebClient;
    }

    public List<Country> getCountries() {
        LOGGER.info("Get Countries");
        return greenWebClient
                .get()
                .uri("green/countries")
                .retrieve()
                .bodyToFlux(Country.class)
                .collectList()
                .block();
    }

    public List<Country> getCountriesAsHal() {
        LOGGER.info("Get Countries with HAL");

        final ParameterizedTypeReference<Resources<Resource<Country>>> countriesResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resources<Resource<Country>> countryResources = greenHalWebClient
                .get()
                .uri("green/countries")
                .retrieve()
                .bodyToMono(countriesResourceTypeReference)
                .block();

        return countryResources
                .getContent()
                .stream()
                .map(Resource::getContent)
                .collect(Collectors.toList());
    }

    public Country getCountry(final Long id) {
        LOGGER.info("Get Country {}", id);
        return greenWebClient
                .get()
                .uri("green/countries/{id}", id)
                .retrieve()
                .bodyToMono(Country.class)
                .block();
    }

    public Country getCountryAsHal(final Long id) {
        LOGGER.info("Get Country {} with HAL", id);

        final ParameterizedTypeReference<Resource<Country>> countryResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resource<Country> countryResource = greenHalWebClient
                .get()
                .uri("green/countries/{id}", id)
                .retrieve()
                .bodyToMono(countryResourceTypeReference)
                .block();

        return countryResource.getContent();
    }

}
