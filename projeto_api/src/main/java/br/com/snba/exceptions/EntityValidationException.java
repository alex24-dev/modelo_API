package br.com.snba.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class EntityValidationException extends RuntimeException {

    private List<String> fieldErrors;

    public EntityValidationException(List<String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public EntityValidationException(List<String> fieldErrors, Throwable cause) {
        super(cause);
        this.fieldErrors = fieldErrors;
    }
}
