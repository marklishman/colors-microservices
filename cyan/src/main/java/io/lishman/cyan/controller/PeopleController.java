package io.lishman.cyan.controller;

import io.lishman.cyan.model.Person;
import io.lishman.cyan.service.PeopleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/people")
class PeopleController {

    final PeopleService peopleService;

    PeopleController(final PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    // ~~~~ RestTemplate

    @GetMapping
    ResponseEntity<List<Person>> getPeople() {
        final List<Person> countries = peopleService.getPeople();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("{id}")
    ResponseEntity<Person> getPersonById(@PathVariable("id") final Long id) {
        final Person person = peopleService.getPerson(id);
        return ResponseEntity.ok(person);
    }

    // ~~~~ Feign

    @GetMapping(params = "client=feign")
    ResponseEntity<List<Person>> getPeopleWithFeign() {
        final List<Person> countries = peopleService.getPeopleWithFeign();
        return ResponseEntity.ok(countries);
    }

    @GetMapping(value = "{id}", params = "client=feign")
    ResponseEntity<Person> getPersonByIdWithFeign(@PathVariable("id") final Long id) {
        final Person person = peopleService.getPersonWithFeign(id);
        return ResponseEntity.ok(person);
    }

}
