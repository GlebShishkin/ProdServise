package ru.stepup.prodservise.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private int errorCode;
    private String errorMessage;

    public NotFoundException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }
}
