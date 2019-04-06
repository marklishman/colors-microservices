package io.lishman.cyan.controller;

import io.lishman.cyan.model.Person;
import io.lishman.cyan.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/people")
class PeopleController {

    final PersonService countryService;

    PeopleController(PersonService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    ResponseEntity<List<Person>> getUsers() {
        final List<Person> countries = countryService.getPeople();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("{id}")
    ResponseEntity<Person> getUserById(@PathVariable("id") final Long id) {
        final Person country = countryService.getPerson(id);
        return ResponseEntity.ok(country);
    }

}
