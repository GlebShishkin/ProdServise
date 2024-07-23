package ru.stepup.prodservise.dto;

import java.math.BigDecimal;

public record PaymentDto (Long prodId, BigDecimal saldo) {
}
