package io.lishman.cyan.service;

import io.lishman.cyan.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  RestTemplate
 */

@Service
public final class PeopleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeopleService.class);

    private final RestTemplate greenRestTemplate;

    public PeopleService(final RestTemplate greenRestTemplate) {
        this.greenRestTemplate = greenRestTemplate;
    }

    public List<Person> getPeople() {
        LOGGER.info("Get People with HAL");

        final ParameterizedTypeReference<Resources<Resource<Person>>> peopleResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resources<Resource<Person>> peopleResources = greenRestTemplate
                .exchange("/people", HttpMethod.GET, null, peopleResourceTypeReference)
                .getBody();

        return peopleResources
                .getContent()
                .stream()
                .map(Resource::getContent)
                .collect(Collectors.toList());
    }

    public Person getPerson(final Long id) {
        LOGGER.info("Get Person {} with HAL", id);

        final ParameterizedTypeReference<Resource<Person>> personResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resource<Person> personResource = greenRestTemplate
                .exchange("/people/{id}", HttpMethod.GET, null, personResourceTypeReference, id)
                .getBody();

        return personResource.getContent();
    }
}
