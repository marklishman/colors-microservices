package io.lishman.green.controller.country;

import io.lishman.green.model.Country;
import io.lishman.green.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
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
@RequestMapping("/countries")
class CountriesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountriesController.class);
    private final CountryService countryService;

    @Autowired
    public CountriesController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Country>> getCountries() {
        LOGGER.info("Get all countries");
        final List<Country> countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Resources<CountryResource>> getCountriesWithHal() {
        LOGGER.info("Get all countries with HAL");
        final List<Country> countries = countryService.getAllCountries();

        final List<CountryResource> countryResourceList = CountryResourceAssembler
                .getInstance()
                .toResources(countries);

        /**
         * NOTE that the collection link is being added manually here.
         * In other words we are not using a ResourceAssembler.
         *
         * There are some significant changes regarding this in the
         * next version of Spring.
         */
        final Resources<CountryResource> countryResources = new Resources<>(countryResourceList);
        countryResources.add(linkTo(methodOn(getClass()).getCountriesWithHal()).withSelfRel());
        return ResponseEntity.ok(countryResources);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Country> getCountry(@PathVariable("id") final Long id) {
        LOGGER.info("Get country for id {} with HAL", id);
        final var country = countryService.getCountryById(id);
        return ResponseEntity.ok(country);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CountryResource> getCountryWithHal(@PathVariable("id") final Long id) {
        LOGGER.info("Get country for id {} with HAL", id);
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
