package ru.stepup.payservise.dto;

import java.math.BigDecimal;

public record ResposeProductDto(Long id,Long userid, String account, BigDecimal saldo, String typ) {

}
