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
    private final String fullName;
    private final boolean isAdult;

    public PersonResource(final Long id,
                          final String firstName,
                          final String lastName,
                          final int age,
                          final String fullName,
                          final boolean isAdult) {
        this.id = id;
        this.firstName = firstName;
        LastName = lastName;
        this.age = age;
        this.fullName = fullName;
        this.isAdult = isAdult;
    }

    public static PersonResource fromPerson(final Person person) {
        return new PersonResource(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getAge(),
                person.getFullName(),
                person.isAdult());
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

    public String getFullName() {
        return fullName;
    }

    public boolean isAdult() {
        return isAdult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonResource that = (PersonResource) o;
        return age == that.age &&
                isAdult == that.isAdult &&
                Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(LastName, that.LastName) &&
                Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, firstName, LastName, age, fullName, isAdult);
    }

    @Override
    public String toString() {
        return "PersonResource{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", age=" + age +
                ", fullName='" + fullName + '\'' +
                ", isAdult=" + isAdult +
                '}';
    }
}
