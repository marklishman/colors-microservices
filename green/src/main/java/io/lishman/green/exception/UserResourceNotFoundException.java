package io.lishman.green.exception;

public class UserResourceNotFoundException extends ResourceNotFoundException {

    public UserResourceNotFoundException(final Long id) {
        super(String.format("User %s not found", id));
    }
}
