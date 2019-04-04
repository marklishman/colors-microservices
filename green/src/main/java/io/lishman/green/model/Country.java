package io.lishman.green.model;

import io.lishman.green.entity.CountryEntity;
import io.lishman.green.entity.PersonEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Country {

    private final Long id;
    private final String code;
    private final String name;
    private final List<PersonEntity> people;

    private Country(final Long id,
                    final String code,
                    final String name,
                    final List<PersonEntity> people) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.people = people;
    }

    public static Country newInstance(final Long id,
                                      final String code,
                                      final String name,
                                      final List<PersonEntity> people) {
        return new Country(id, code, name, people);
    }

    public static Country fromCountryEntity(CountryEntity countryEntity) {
        return new Country (
                countryEntity.getId(),
                countryEntity.getCode(),
                countryEntity.getName(),
                countryEntity.getPeople()
        );
    }

    public Country cloneWithNewId(final Long id) {
        return Country.newInstance(id, code, name, people);
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<PersonEntity> getPeople() {
        return Collections.unmodifiableList(people);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(code, country.code) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
