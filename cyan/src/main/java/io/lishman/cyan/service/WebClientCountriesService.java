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

@Service
public final class WebClientCountriesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientCountriesService.class);

    private static final ParameterizedTypeReference<Resource<Country>> COUNTRY_TYPE_REF = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<Resources<Resource<Country>>> COUNTRIES_TYPE_REF = new ParameterizedTypeReference<>() {};

    private final WebClient greenWebClient;
    private final WebClient greenHalWebClient;

    public WebClientCountriesService(final WebClient greenWebClient,
                                     final WebClient greenHalWebClient) {
        this.greenWebClient = greenWebClient;
        this.greenHalWebClient = greenHalWebClient;
    }

    public List<Country> getCountries() {
        LOGGER.info("Get Countries with WebClient");
        return greenWebClient
                .get()
                .uri("green/countries")
                .retrieve()
                .bodyToFlux(Country.class)
                .collectList()
                .block();
    }

    public List<Country> getCountriesAsHal() {
        LOGGER.info("Get Countries with WebClient as HAL");
        return greenHalWebClient
                .get()
                .uri("green/countries")
                .retrieve()
                .bodyToMono(COUNTRIES_TYPE_REF)
                .map(ClientUtils::getResourcesContent)
                .block();
    }

    public Country getCountry(final Long id) {
        LOGGER.info("Get Country {} with WebClient", id);
        return greenWebClient
                .get()
                .uri("green/countries/{id}", id)
                .retrieve()
                .bodyToMono(Country.class)
                .block();
    }

    public Country getCountryAsHal(final Long id) {
        LOGGER.info("Get Country {} with WebClient as HAL", id);
        return greenHalWebClient
                .get()
                .uri("green/countries/{id}", id)
                .retrieve()
                .bodyToMono(COUNTRY_TYPE_REF)
                .map(Resource::getContent)
                .block();
    }

    public Country createCountry(final Country country) {
        LOGGER.info("Create Country with WebClient");
        return greenWebClient.post()
                .uri("green/countries")
                .syncBody(country)
                .retrieve()
                .bodyToMono(Country.class)
                .block();
    }

    public Country updateCountry(final Long id, final Country country) {
        LOGGER.info("Update Country {} with WebClient", id);
        final Country updatedCountry = country.cloneWithNewId(id);
        return greenWebClient.put()
                .uri("green/countries/{id}", id)
                .syncBody(updatedCountry)
                .retrieve()
                .bodyToMono(Country.class)
                .block();
    }

    public void deleteCountry(final Long id) {
        LOGGER.info("Delete Country {} with WebClient", id);
        greenWebClient.delete()
                .uri("green/countries/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
