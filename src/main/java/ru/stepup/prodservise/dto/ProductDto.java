package ru.stepup.prodservise.dto;

import ru.stepup.prodservise.enumerator.ProdType;

import java.math.BigDecimal;

public record ProductDto (Long id, Long userid, String account, BigDecimal saldo, ProdType typ) {
}
