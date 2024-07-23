package ru.stepup.prodservise.dto;

import java.math.BigDecimal;

public record PaymentResponseDto (String account, BigDecimal saldo) {
}
