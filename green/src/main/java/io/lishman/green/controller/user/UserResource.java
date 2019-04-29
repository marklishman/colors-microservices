package io.lishman.green.controller.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.lishman.green.model.User;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Objects;

@Relation(value = "user", collectionRelation = "users")
final public class UserResource extends ResourceSupport {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String email;
    private final String phoneNumber;
    private final Integer age;
    private final String website;

    private UserResource(final Long id,
                         final String firstName,
                         final String lastName,
                         final String userName,
                         final String email,
                         final String phoneNumber,
                         final Integer age,
                         final String website) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.website = website;
    }

    @JsonCreator
    public static UserResource jsonCreator(@JsonProperty("firstName") final String firstName,
                                           @JsonProperty("lastName") final String lastName,
                                           @JsonProperty("userName") final String userName,
                                           @JsonProperty("email") final String email,
                                           @JsonProperty("phoneNumber") final String phoneNumber,
                                           @JsonProperty("age") final Integer age,
                                           @JsonProperty("website") final String website) {
        return new UserResource(
                null,
                firstName,
                lastName,
                userName,
                email,
                phoneNumber,
                age,
                website
        );
    }

    public static UserResource fromUser(final User user) {
        return new UserResource(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAge(),
                user.getWebsite()
        );
    }

    @JsonProperty("id")
    public Long getUserId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getAge() {
        return age;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserResource that = (UserResource) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(age, that.age) &&
                Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, firstName, lastName, userName, email, phoneNumber, age, website);
    }

    @Override
    public String toString() {
        return "UserResource{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", website='" + website + '\'' +
                '}';
    }
}
