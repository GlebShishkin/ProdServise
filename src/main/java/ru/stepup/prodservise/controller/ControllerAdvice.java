package ru.stepup.prodservise.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.stepup.prodservise.dto.ErrorResponseDto;
import ru.stepup.prodservise.exceptions.InsufficientFundsException;
import ru.stepup.prodservise.exceptions.NotFoundException;
import ru.stepup.prodservise.exceptions.UniException;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    // ТЗ "Добавить возврат ошибок клиенту о проблемах как на стороне платежного сервиса, так и на стороне сервиса продуктов"
    @ExceptionHandler(UniException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleItemException(UniException exception) {
//        log.info("#####################/api ControllerAdvice.UniException: exception.getErrorMessage(): = " + exception.getMessage());
        return new ErrorResponseDto(exception.getHttpStatus().value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleException(NotFoundException exception) {
//        log.info("#####################/api ControllerAdvice: exception.getErrorMessage(): = " + exception.getErrorMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getErrorMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleException(InsufficientFundsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getErrorMessage());
    }
}
