package io.lishman.green.model;

import io.lishman.green.entity.CountryEntity;

import java.util.Objects;

public final class Country {

    private final Long id;
    private final String code;
    private final String name;

    private Country(final Long id,
                    final String code,
                    final String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public static Country newInstance(final Long id,
                                      final String code,
                                      final String name) {
        return new Country(id, code, name);
    }

    public static Country fromCountryEntity(CountryEntity countryEntity) {
        return new Country (
                countryEntity.getId(),
                countryEntity.getCode(),
                countryEntity.getName()
        );
    }

    public Country cloneWithNewId(final Long id) {
        return Country.newInstance(id, code, name);
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
