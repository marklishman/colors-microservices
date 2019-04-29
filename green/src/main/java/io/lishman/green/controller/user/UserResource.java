package io.lishman.green.controller.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.lishman.green.model.User;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Objects;

@Relation(value = "user", collectionRelation = "users")
final class UserResource extends ResourceSupport {

    private final User user;

    private UserResource(final User user) {
        this.user = user;
    }

    @JsonCreator
    public static UserResource jsonCreator(@JsonProperty("id") final Long id,
                                           @JsonProperty("firstName") final String firstName,
                                           @JsonProperty("lastName") final String lastName,
                                           @JsonProperty("userName") final String userName,
                                           @JsonProperty("email") final String email,
                                           @JsonProperty("phoneNumber") final String phoneNumber,
                                           @JsonProperty("age") final Integer age,
                                           @JsonProperty("website") final String website) {
        final User user = User.newInstance(id, firstName, lastName, userName, email, phoneNumber, age, website);
        return new UserResource(user);
    }

    public static UserResource fromUser(final User user) {
        return new UserResource(user);
    }

    public User getUser() {
        return user;
    }

    @JsonProperty("id")
    public Long getPersonId() {
        return user.getId();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public String getUserName() {
        return user.getUserName();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }

    public Integer getAge() {
        return user.getAge();
    }

    public String getWebsite() {
        return user.getWebsite();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserResource that = (UserResource) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user);
    }

    @Override
    public String toString() {
        return "UserResource{" +
                "user=" + user +
                '}';
    }
}
