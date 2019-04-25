package io.lishman.cyan.controller;

import io.lishman.cyan.model.Person;
import io.lishman.cyan.service.FeignPeopleService;
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
    private final FeignPeopleService feignPeopleService;

    PeopleController(final RestTemplatePeopleService restTemplatePeopleService,
                     final FeignPeopleService feignPeopleService) {
        this.restTemplatePeopleService = restTemplatePeopleService;
        this.feignPeopleService = feignPeopleService;
    }

    // ~~~~ RestTemplate

    @GetMapping
    ResponseEntity<List<Person>> getPeople() {
        final List<Person> countries = restTemplatePeopleService.getPeople();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("{id}")
    ResponseEntity<Person> getPersonById(@PathVariable("id") final Long id) {
        final Person person = restTemplatePeopleService.getPerson(id);
        return ResponseEntity.ok(person);
    }

    // ~~~~ Feign

    @GetMapping(params = "client=feign")
    ResponseEntity<List<Person>> getPeopleWithFeign() {
        final List<Person> countries = feignPeopleService.getPeople();
        return ResponseEntity.ok(countries);
    }

    @GetMapping(value = "{id}", params = "client=feign")
    ResponseEntity<Person> feigetPersonByIdWithFeign(@PathVariable("id") final Long id) {
        final Person person = feignPeopleService.getPerson(id);
        return ResponseEntity.ok(person);
    }

}
