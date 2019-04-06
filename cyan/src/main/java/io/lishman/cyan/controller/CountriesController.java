package io.lishman.cyan.controller;

import io.lishman.cyan.service.CountryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
class CountriesController {

    final CountryService countryService;

    CountriesController(CountryService countryService) {
        this.countryService = countryService;
    }

//    @GetMapping
//    ResponseEntity<Flux<Country>> getUsers() {
//        return countryService.getUsers();
//    }

}
