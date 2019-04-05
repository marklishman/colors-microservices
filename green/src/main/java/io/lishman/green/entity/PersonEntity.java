package io.lishman.green.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.lishman.green.model.Person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="person")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "psn_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "psn_first_name")
    private String firstName;

    @Column(name = "psn_last_name")
    private String lastName;

    @Column(name = "psn_age")
    private Integer age;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "visitor",
            joinColumns = @JoinColumn(name = "psn_id"),
            inverseJoinColumns = @JoinColumn(name = "cty_id"))
    private List<CountryEntity> countries;

    public PersonEntity() {
    }

    private PersonEntity(final String firstName,
                         final String lastName,
                         final Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public static PersonEntity fromPerson(final Person person) {
        return new PersonEntity(
                person.getFirstName(),
                person.getLastName(),
                person.getAge()
        );
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public List<CountryEntity> getCountries() {
        return countries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", countries=" + countries +
                '}';
    }
}
