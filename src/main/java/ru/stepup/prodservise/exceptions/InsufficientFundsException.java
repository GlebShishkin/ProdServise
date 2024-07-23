package ru.stepup.prodservise.exceptions;

import lombok.Getter;

@Getter
public class InsufficientFundsException extends RuntimeException {
    private int errorCode;
    private String errorMessage;

    public InsufficientFundsException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }
}
