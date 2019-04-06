package io.lishman.cyan.service;

import io.lishman.cyan.model.Person;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public final class PersonService {

    public List<Person> getPeople() {

        final WebClient webClient = WebClient
                .builder()
                .baseUrl("http://localhost:8021")
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient
                .get()
                .uri("green/people")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Person.class)
                .collectList()
                .block();
    }

    public Person getPerson(final Long id) {

        final WebClient webClient = WebClient
                .builder()
                .baseUrl("http://localhost:8021")
                .build();

        return webClient
                .get()
                .uri("green/people/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }

}
