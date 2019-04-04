package io.lishman.green.controller.country;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.lishman.green.entity.PersonEntity;
import io.lishman.green.model.Country;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.List;

@Relation(value = "country", collectionRelation = "Countries")
final class CountryResource extends ResourceSupport {

    private final Long id;
    private final String code;
    private final String name;
    private final List<PersonEntity> people;

    private CountryResource(Long id, String code, String name, List<PersonEntity> people) {
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
}
