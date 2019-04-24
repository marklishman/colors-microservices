package io.lishman.cyan.controller;

import io.lishman.cyan.model.Country;
import io.lishman.cyan.service.CountriesService;
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

@RestController
@RequestMapping("/countries")
class CountriesController {

    final CountriesService countriesService;

    CountriesController(final CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    // ~~~~ JSON

    @GetMapping
    ResponseEntity<List<Country>> getUsers() {
        final List<Country> countries = countriesService.getCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping(value = "{id}")
    ResponseEntity<Country> getUserById(@PathVariable("id") final Long id) {
        final Country country = countriesService.getCountry(id);
        return ResponseEntity.ok(country);
    }

    // ~~~~ HAL

    @GetMapping(params = "client=hal")
    ResponseEntity<List<Country>> getUsersWithHal() {
        final List<Country> countries = countriesService.getCountriesAsHal();
        return ResponseEntity.ok(countries);
    }

    @GetMapping(value = "{id}", params = "client=hal")
    ResponseEntity<Country> getUserByIdWithHal(@PathVariable("id") final Long id) {
        final Country country = countriesService.getCountryAsHal(id);
        return ResponseEntity.ok(country);
    }

    // ~~~~ POST

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Country createCountry(@RequestBody final Country country) {
        return countriesService.createCountry(country);
    }

    // ~~~~ PUT

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Country updateDetails(
            @PathVariable("id") final Long id,
            @RequestBody final Country country) {
        return countriesService.updateCountry(id, country);
    }

}
