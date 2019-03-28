package io.lishman.purple.controller;

import io.lishman.purple.entity.CountryEntity;
import io.lishman.purple.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class CountryController {

    private final CountryRepository countryRepository;
    private final EntityLinks entityLinks;

    @Autowired
    public CountryController(final CountryRepository countryRepository, final EntityLinks entityLinks) {
        this.countryRepository = countryRepository;
        this.entityLinks = entityLinks;
    }

    @GetMapping("countries/{code}/visitors")
    public ResponseEntity<?> visitors(@PathVariable("code") final String code)  {

        final Optional<CountryEntity> country = countryRepository.findByCode(code);
        if (country.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final List<Resource> visitors = country.get()
                .getPeople()
                .stream()
                .map(Resource::new)
                .collect(Collectors.toList());

        final Resources<Resource> resources = new Resources<>(visitors);
        resources.add(linkTo(methodOn(CountryController.class).visitors(null)).withSelfRel());
        resources.add(entityLinks.linkToSingleResource(CountryEntity.class, country.get().getId()).withRel("country"));
        return ResponseEntity.ok(resources);
    }
}
