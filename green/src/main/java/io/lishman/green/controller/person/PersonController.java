package io.lishman.green.controller.person;

import io.lishman.green.model.Person;
import io.lishman.green.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/people")
class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> getPeople() {
        LOGGER.info("Get all people");
        final List<Person> people = personService.getAllCountries();
        return ResponseEntity.ok(people);
    }

    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<Resources<PersonResource>> getPeopleWithHal() {
        LOGGER.info("Get all people with HAL");
        final List<Person> persons = personService.getAllCountries();

        final List<PersonResource> personResourceList = PersonResourceAssembler
                .getInstance()
                .toResources(persons);

        /**
         * NOTE that the collection link is being added manually here.
         * In other words we are not using a ResourceAssembler.
         *
         * There are some significant changes regarding this in the
         * next version of Spring.
         */
        final Resources<PersonResource> personResources = new Resources<>(personResourceList);
        personResources.add(linkTo(methodOn(getClass()).getPeopleWithHal()).withSelfRel());
        return ResponseEntity.ok(personResources);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getPerson(@PathVariable("id") final Long id) {
        LOGGER.info("Get person for id {}", id);
        final var person = personService.getPersonById(id);
        return ResponseEntity.ok(person);

    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<PersonResource> getPersonWithHal(@PathVariable("id") final Long id) {
        LOGGER.info("Get person for id {} with HAL", id);
        final var person = personService.getPersonById(id);
        var personResource = PersonResourceAssembler
                .getInstance()
                .toResource(person);
        return ResponseEntity.ok(personResource);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody final Person person) {
        // TODO logging
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Person updateDetails(
            @PathVariable("id") final Long id,
            @RequestBody final Person person) {
        // TODO logging
        return personService.updatePerson(id, person);
    }
}
