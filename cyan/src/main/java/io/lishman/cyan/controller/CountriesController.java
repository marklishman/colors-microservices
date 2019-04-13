package io.lishman.cyan.controller;

import io.lishman.cyan.model.Country;
import io.lishman.cyan.service.CountriesService;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/countries")
class CountriesController {

    final CountriesService countriesService;

    CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Country>> getUsers() {
        final List<Country> countries = countriesService.getCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<List<Country>> getUsersWithHal() {
        final List<Country> countries = countriesService.getCountriesAsHal();
        return ResponseEntity.ok(countries);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Country> getUserById(@PathVariable("id") final Long id) {
        final Country country = countriesService.getCountry(id);
        return ResponseEntity.ok(country);
    }

    @GetMapping(value = "{id}", produces = MediaTypes.HAL_JSON_VALUE)
    ResponseEntity<Country> getUserByIdWithHal(@PathVariable("id") final Long id) {
        final Country country = countriesService.getCountryAsHal(id);
        return ResponseEntity.ok(country);
    }

}
