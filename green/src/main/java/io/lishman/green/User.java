package io.lishman.green;

public final class User {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String userName;

    private User(final Long id,
                 final String firstName,
                 final String lastName,
                 final String userName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public static User newInstance(final Long id,
                                   final String firstName,
                                   final String lastName,
                                   final String userName) {
        return new User(id, firstName, lastName, userName);
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
}
