package io.lishman.green.controller.country;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.lishman.green.entity.PersonEntity;
import io.lishman.green.model.Country;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.List;
import java.util.Objects;

@Relation(value = "country", collectionRelation = "countries")
final class CountryResource extends ResourceSupport {

    private final Long id;
    private final String code;
    private final String name;
    private final List<PersonEntity> people;

    private CountryResource(final Long id,
                            final String code,
                            final String name,
                            final List<PersonEntity> people) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.people = people;
    }

    public static CountryResource fromCountry(final Country country) {
        return new CountryResource(
                country.getId(),
                country.getCode(),
                country.getName(),
                country.getPeople()
        );
    }

    @JsonProperty("id")
    public Long getCountryId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<PersonEntity> getPeople() {
        return people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CountryResource that = (CountryResource) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, code, name);
    }

    @Override
    public String toString() {
        return "CountryResource{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
