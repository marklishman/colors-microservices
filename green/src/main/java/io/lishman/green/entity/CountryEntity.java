package io.lishman.green.entity;

import io.lishman.green.model.Country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "country")
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cty_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "cty_code")
    private String code;

    @Column(name = "cty_name")
    private String name;

    @ManyToMany(mappedBy = "countries")
    private List<PersonEntity> people = new ArrayList<>();

    public CountryEntity() {
    }

    private CountryEntity(final String code,
                          final String name) {
        this.code = code;
        this.name = name;
    }

    public static CountryEntity fromCountry(final Country country) {
        return new CountryEntity(
                country.getCode(),
                country.getName()
        );
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
        return people;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryEntity that = (CountryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
