package io.lishman.green.service;

import io.lishman.green.entity.CountryEntity;
import io.lishman.green.model.Country;
import io.lishman.green.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RefreshScope
public class CountryService {

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${app.config:default green config}")
    private String config;

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(final CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(Country::fromCountryEntity)
                .collect(Collectors.toList());
    }

    public Country getCountryById(final Long id) {
        return countryRepository.findById(id)
                .map(Country::fromCountryEntity)
                .orElseThrow();
    }

    public Country createCountry(final Country country) {
        final var countryEntity = CountryEntity.fromCountry(country);
        final var savedCountryEntity = this.countryRepository.save(countryEntity);
        return Country.fromCountryEntity(savedCountryEntity);
    }

    public Country updateCountry(final Long id, final Country country) {
        final var countryWithId = country.cloneWithNewId(id);
        return this.createCountry(countryWithId);
    }
}
