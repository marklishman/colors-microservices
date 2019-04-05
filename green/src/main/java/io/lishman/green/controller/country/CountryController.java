package io.lishman.green.controller.country;

import io.lishman.green.model.Country;
import io.lishman.green.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/countries")
final class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping()
    public ResponseEntity<Resources<CountryResource>> getCountrys() {
        LOGGER.info("Get all countrys");
        final List<Country> countrys = countryService.getAllCountries();

        final List<CountryResource> countryResourceList = CountryResourceAssembler
                .getInstance()
                .toResources(countrys);

        /**
         * NOTE that the collection link is being added manually here.
         * In other words we are not using a ResourceAssembler.
         *
         * There are some significant changes regarding this in the
         * next version of Spring.
         */
        final Resources<CountryResource> countryResources = new Resources<>(countryResourceList);
        countryResources.add(linkTo(methodOn(getClass()).getCountrys()).withSelfRel());
        return ResponseEntity.ok(countryResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResource> getCountry(@PathVariable("id") final Long id) {
        LOGGER.info("Get country for id {}", id);
        final var country = countryService.getCountryById(id);
        var countryResource = CountryResourceAssembler
                .getInstance()
                .toResource(country);
        return ResponseEntity.ok(countryResource);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Country createCountry(@RequestBody final Country country) {
        // TODO logging
        return countryService.createCountry(country);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Country updateDetails(
            @PathVariable("id") final Long id,
            @RequestBody final Country country) {
        // TODO logging
        return countryService.updateCountry(id, country);
    }
}
