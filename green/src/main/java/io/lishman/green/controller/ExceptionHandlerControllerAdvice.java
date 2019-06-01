package io.lishman.green.controller;

import io.lishman.green.exception.SomeSpecialException;
import io.lishman.green.exception.CustomExceptionBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = SomeSpecialException.class)
    protected ResponseEntity<CustomExceptionBody> handleSpecialCase(final SomeSpecialException ex, final WebRequest request) {

        final CustomExceptionBody body = new CustomExceptionBody(ex.getCode(), ex.getDescription());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
