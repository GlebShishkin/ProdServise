package ru.stepup.prodservise.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stepup.prodservise.enumerator.ProdType;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class Product
{
    private Long id;
    private Long userid;
    private String account;
    private BigDecimal saldo;
    private ProdType typ;

    public Product(Long userid, String account, BigDecimal saldo, ProdType typ) {
        this.userid = userid;
        this.account = account;
        this.saldo = saldo;
        this.typ = typ;
    }
}
