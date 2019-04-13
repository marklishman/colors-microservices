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

    PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    ResponseEntity<List<Person>> getUsers() {
        final List<Person> countries = peopleService.getPeopleUsingRestTemplate();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("{id}")
    ResponseEntity<Person> getUserById(@PathVariable("id") final Long id) {
        final Person country = peopleService.getPersonUsingRestTemplate(id);
        return ResponseEntity.ok(country);
    }

}
