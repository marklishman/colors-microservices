package io.lishman.cyan.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class User {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String email;
    private final String phoneNumber;
    private final Integer age;
    private final String website;

    private User(final Long id,
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

    public static User newInstance(final Long id,
                                   final String firstName,
                                   final String lastName,
                                   final String userName,
                                   final String email,
                                   final String phoneNumber,
                                   final Integer age,
                                   final String website) {
        return new User(id, firstName, lastName, userName, email, phoneNumber, age, website);
    }

    @JsonCreator
    public static User jsonCreator(@JsonProperty("id") final Long id,
                                   @JsonProperty("firstName") final String firstName,
                                   @JsonProperty("lastName") final String lastName,
                                   @JsonProperty("userName") final String userName,
                                   @JsonProperty("email") final String email,
                                   @JsonProperty("phoneNumber") final String phoneNumber,
                                   @JsonProperty("age") final Integer age,
                                   @JsonProperty("website") final String website) {
        return newInstance(id, firstName, lastName, userName, email, phoneNumber, age, website);
    }

    public User cloneWithNewId(final Long id) {
        return User.newInstance(id, firstName, lastName, userName, email, phoneNumber, age, website);
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
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(age, user.age) &&
                Objects.equals(website, user.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, userName, email, phoneNumber, age, website);
    }

    @Override
    public String toString() {
        return "User{" +
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
