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

@Service
public final class RestTemplatePeopleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplatePeopleService.class);

    private final RestTemplate purpleRestTemplate;

    // Person excerpt
    private static class PersonFullName {
        public String fullName;

        String getFullName() {
            return fullName;
        }
    }

    public RestTemplatePeopleService(final RestTemplate purpleRestTemplate) {
        this.purpleRestTemplate = purpleRestTemplate;
    }

    public List<String> getPeopleNames() {
        LOGGER.info("Get People names with RestTemplate as HAL");

        final ParameterizedTypeReference<Resources<Resource<PersonFullName>>> peopleResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resources<Resource<PersonFullName>> peopleResources = purpleRestTemplate
                .exchange("/people", HttpMethod.GET, null, peopleResourceTypeReference)
                .getBody();

        return peopleResources
                .getContent()
                .stream()
                .map(Resource::getContent)
                .map(PersonFullName::getFullName)
                .collect(Collectors.toList());
    }

    public Person getPerson(final Long id) {
        LOGGER.info("Get Person {} with RestTemplate as HAL", id);

        final ParameterizedTypeReference<Resource<Person>> personResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resource<Person> personResource = purpleRestTemplate
                .exchange("/people/{id}", HttpMethod.GET, null, personResourceTypeReference, id)
                .getBody();

        return personResource.getContent();
    }
}
