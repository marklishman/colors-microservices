package io.lishman.purple.controller;

import io.lishman.purple.entity.CountryEntity;
import io.lishman.purple.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class CustomCountryController {

    private final CountryRepository countryRepository;

    @Autowired
    public CustomCountryController(final CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("countries/search/findByCountryCode/{code}")
    public ResponseEntity<?> customCountries(@PathVariable("code") final String code)  {
        final Optional<CountryEntity> country = countryRepository.findByCode(code);
        if (country.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final Resource<CountryEntity> resource = new Resource<>(country.get());
        resource.add(linkTo(methodOn(CustomCountryController.class).customCountries(null)).withSelfRel());
        return ResponseEntity.ok(resource);
    }
}
