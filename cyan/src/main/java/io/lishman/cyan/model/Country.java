package io.lishman.cyan.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonCreator
    public static Country newInstance(@JsonProperty("id") final Long id,
                                      @JsonProperty("code") final String code,
                                      @JsonProperty("name") final String name) {
        return new Country(id, code, name);
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
