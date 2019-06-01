package io.lishman.green.exception;

public class SomeSpecialException extends RuntimeException {

    private final int code;
    private final String description;

    public SomeSpecialException(final int code, final String descriptiion) {
        this.code = code;
        this.description = descriptiion;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
