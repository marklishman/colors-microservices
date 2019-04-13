package io.lishman.cyan.service;

import io.lishman.cyan.model.Person;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.client.Hop.rel;

@Service
public final class PersonService {

    private final WebClient greenHalWebClient;
    private final RestTemplate restTemplate;

    public PersonService(final WebClient greenHalWebClient, final RestTemplate restTemplate) {
        this.greenHalWebClient = greenHalWebClient;
        this.restTemplate = restTemplate;
    }

    // ~~~~ WebClient

    public List<Person> getPeopleUsingWebClient() {

        final ParameterizedTypeReference<Resources<Resource<Person>>> peopleResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resources<Resource<Person>> peopleResources = greenHalWebClient
                .get()
                .uri("green/people")
                .retrieve()
                .bodyToMono(peopleResourceTypeReference)
                .block();

        List<Person> people = peopleResources
                .getContent()
                .stream()
                .map(resource -> resource.getContent())
                .collect(Collectors.toList());

        return people;
    }

    // ~~~~ Traverson

    public Collection<Person> getPeopleUsingTraverson() {

        final ParameterizedTypeReference<Resources<Person>> peopleResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        Traverson traverson = null;
        try {
            traverson = new Traverson(new URI("http://localhost:8021/green/people"), MediaTypes.HAL_JSON);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Resources<Person> peopleResource = traverson
                .follow(rel("self"))
                .toObject(peopleResourceTypeReference);

        return peopleResource.getContent();
    }

    // ~~~~ RestTemplate

    public List<Person> getPeopleUsingRestTemplate() {

        final ParameterizedTypeReference<Resources<Resource<Person>>> peopleResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resources<Resource<Person>> peopleResources = restTemplate
                .exchange("http://localhost:8021/green/people", HttpMethod.GET, null, peopleResourceTypeReference)
                .getBody();

        List<Person> people = peopleResources
                .getContent()
                .stream()
                .map(resource -> resource.getContent())
                .collect(Collectors.toList());

        return people;
    }

    // ~~~~ WebClient

    public Person getPersonUsingWebClient(final Long id) {

        final ParameterizedTypeReference<Resource<Person>> personResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resource<Person> personResource = greenHalWebClient
                .get()
                .uri("green/people/{id}", id)
                .retrieve()
                .bodyToMono(personResourceTypeReference)
                .block();

        return personResource.getContent();
    }

    // ~~~~ Traverson

    public Person getPersonUsingTraverson(final Long id) {

        final ParameterizedTypeReference<Resource<Person>> personResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        Traverson traverson = null;
        try {
            traverson = new Traverson(new URI("http://localhost:8021/green/people"), MediaTypes.HAL_JSON);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        Resource<Person> personResource = traverson
                .follow("$._embedded.people[3]._links.self.href")
                .toObject(personResourceTypeReference);

        return personResource.getContent();
    }

    // ~~~~ RestTemplate

    public Person getPersonUsingRestTemplate(final Long id) {

        final ParameterizedTypeReference<Resource<Person>> personResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resource<Person> personResource = restTemplate
                .exchange("http://localhost:8021/green/people/{id}", HttpMethod.GET, null, personResourceTypeReference, id)
                .getBody();

        return personResource.getContent();
    }
}
