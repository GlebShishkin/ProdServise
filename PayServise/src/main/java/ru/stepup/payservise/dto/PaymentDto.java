package ru.stepup.payservise.dto;

import java.math.BigDecimal;

public record PaymentDto (Long prodId, BigDecimal saldo) {
}
