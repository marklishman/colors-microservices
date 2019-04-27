package io.lishman.cyan.controller;

import io.lishman.cyan.model.Person;
import io.lishman.cyan.service.RestTemplatePeopleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/people")
class PeopleController {

    private final RestTemplatePeopleService restTemplatePeopleService;

    PeopleController(final RestTemplatePeopleService restTemplatePeopleService) {
        this.restTemplatePeopleService = restTemplatePeopleService;
    }

    // ~~~~ RestTemplate

    @GetMapping(params = "client=resttemplate")
    ResponseEntity<List<String>> getPeople() {
        final List<String> people = restTemplatePeopleService.getPeopleNames();
        return ResponseEntity.ok(people);
    }

    @GetMapping(value = "{id}", params = "client=resttemplate")
    ResponseEntity<Person> getPersonById(@PathVariable("id") final Long id) {
        final Person person = restTemplatePeopleService.getPerson(id);
        return ResponseEntity.ok(person);
    }
}
