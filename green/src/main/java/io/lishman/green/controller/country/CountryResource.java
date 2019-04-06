package io.lishman.green.controller.country;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.lishman.green.model.Country;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Objects;

@Relation(value = "country", collectionRelation = "countries")
final class CountryResource extends ResourceSupport {

    private final Long id;
    private final String code;
    private final String name;

    private CountryResource(final Long id,
                            final String code,
                            final String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public static CountryResource fromCountry(final Country country) {
        return new CountryResource(
                country.getId(),
                country.getCode(),
                country.getName()
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
