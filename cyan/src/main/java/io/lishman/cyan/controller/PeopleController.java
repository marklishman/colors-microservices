package io.lishman.cyan.controller;

import io.lishman.cyan.model.Person;
import io.lishman.cyan.model.User;
import io.lishman.cyan.service.FeignUsersService;
import io.lishman.cyan.service.RestTemplatePeopleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
class PeopleController {

    private final RestTemplatePeopleService restTemplatePeopleService;
    private final FeignUsersService feignUsersService;

    PeopleController(final RestTemplatePeopleService restTemplatePeopleService,
                     final FeignUsersService feignUsersService) {
        this.restTemplatePeopleService = restTemplatePeopleService;
        this.feignUsersService = feignUsersService;
    }

    // ~~~~ RestTemplate

    @GetMapping(params = "client=resttemplate")
    ResponseEntity<List<Person>> getPeople() {
        final List<Person> countries = restTemplatePeopleService.getPeople();
        return ResponseEntity.ok(countries);
    }

    @GetMapping(value = "{id}", params = "client=resttemplate")
    ResponseEntity<Person> getPersonById(@PathVariable("id") final Long id) {
        final Person person = restTemplatePeopleService.getPerson(id);
        return ResponseEntity.ok(person);
    }

    // ~~~~ Feign

    @GetMapping(params = "client=feign")
    ResponseEntity<List<User>> getUsersWithFeign() {
        final List<User> users = feignUsersService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "{id}", params = "client=feign")
    ResponseEntity<User> getUserByIdWithFeign(@PathVariable("id") final Long id) {
        final User user = feignUsersService.getUser(id);
        return ResponseEntity.ok(user);
    }

}
