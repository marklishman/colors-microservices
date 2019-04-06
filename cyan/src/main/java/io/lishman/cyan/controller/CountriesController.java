package io.lishman.cyan.controller;

import io.lishman.cyan.model.Country;
import io.lishman.cyan.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/countries")
class CountriesController {

    final CountryService countryService;

    CountriesController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    ResponseEntity<List<Country>> getUsers() {
        final List<Country> countries = countryService.getCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("{id}")
    ResponseEntity<Country> getUserById(@PathVariable("id") final Long id) {
        final Country country = countryService.getCountry(id);
        return ResponseEntity.ok(country);
    }

}
