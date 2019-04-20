package io.lishman.cyan.service;

import io.lishman.cyan.model.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "people", url = "http://localhost:8021/green/people")
public interface PeopleFeignClient {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Person> getPeople();

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Person getPerson(@PathVariable("id") final Long id);
}
