package io.lishman.green.exception;

public class CustomExceptionBody {

    private final int code;
    private final String description;

    public CustomExceptionBody(final int code, final String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
