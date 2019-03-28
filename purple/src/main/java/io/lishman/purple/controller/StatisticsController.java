package io.lishman.purple.controller;

import io.lishman.purple.repository.CountryRepository;
import io.lishman.purple.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@BasePathAwareController
public class StatisticsController {

    private final CountryRepository countryRepository;
    private final PersonRepository personRepository;
    private final EntityLinks entityLinks;

    @Autowired
    public StatisticsController(final CountryRepository countryRepository,
                                final PersonRepository personRepository,
                                final EntityLinks entityLinks) {
        this.countryRepository = countryRepository;
        this.personRepository = personRepository;
        this.entityLinks = entityLinks;
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats()  {

        final int countryCount = countryRepository.findAll().size();
        final int personCount = personRepository.findAll().size();

        final StatisticsResource countryStatistics = new StatisticsResource("country", countryCount);
        final StatisticsResource personStatistics = new StatisticsResource("person", personCount);

        final Resources<Resource<StatisticsResource>> resources = new Resources<>(
                List.of(
                        new Resource<>(countryStatistics),
                        new Resource<>(personStatistics)
                )
        );
        resources.add(linkTo(methodOn(StatisticsController.class).stats()).withSelfRel());
        return ResponseEntity.ok(resources);
    }
}
