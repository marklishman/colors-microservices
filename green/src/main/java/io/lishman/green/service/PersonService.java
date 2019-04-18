package io.lishman.green.service;

import io.lishman.green.entity.PersonEntity;
import io.lishman.green.model.Person;
import io.lishman.green.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RefreshScope
public class PersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${app.config:default green config}")
    private String config;

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllCountries() {
        return personRepository.findAll()
                .stream()
                .map(Person::fromPersonEntity)
                .collect(Collectors.toList());
    }

    public Person getPersonById(final Long id) {
        return personRepository.findById(id)
                .map(Person::fromPersonEntity)
                .orElseThrow();
    }

    public Person createPerson(final Person person) {
        final var personEntity = PersonEntity.fromPerson(person);
        final var savedPersonEntity = this.personRepository.save(personEntity);
        return Person.fromPersonEntity(savedPersonEntity);
    }

    public Person updatePerson(final Long id, final Person person) {
        final var personWithId = person.cloneWithNewId(id);
        return this.createPerson(personWithId);
    }
}
