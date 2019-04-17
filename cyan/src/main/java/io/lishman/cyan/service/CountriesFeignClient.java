package io.lishman.cyan.service;

import io.lishman.cyan.model.Country;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "countries", url = "http://localhost:8021/green/countries")
public interface CountriesFeignClient {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Country> getCountries();

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Country getCountry(@PathVariable("id") final Long id);

}
