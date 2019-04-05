package io.lishman.green.controller.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.lishman.green.model.Person;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Objects;

@Relation(value = "person", collectionRelation = "people")
final class PersonResource extends ResourceSupport {

    private final Long id;
    private final String firstName;
    private final String LastName;
    private final int age;

    public PersonResource(final Long id,
                          final String firstName,
                          final String lastName,
                          final int age) {
        this.id = id;
        this.firstName = firstName;
        LastName = lastName;
        this.age = age;
    }

    public static PersonResource fromPerson(final Person person) {
        return new PersonResource(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getAge()
        );
    }

    @JsonProperty("id")
    public Long getPersonId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonResource that = (PersonResource) o;
        return age == that.age &&
                Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(LastName, that.LastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, firstName, LastName, age);
    }

    @Override
    public String toString() {
        return "PersonResource{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", age=" + age +
                '}';
    }
}
