package ru.stepup.payservise.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepup.payservise.dto.ResposeProductDto;
import ru.stepup.payservise.servise.PayProductServise;
import ru.stepup.prodservise.dto.PaymentDto;
import ru.stepup.prodservise.dto.PaymentResponseDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pay")
public class PayProductController {

    private PayProductServise payProductServise;

    public PayProductController(PayProductServise payProductServise) {
        this.payProductServise = payProductServise;
    }

    // ТЗ: "Добавить возможность запрашивать продукты у платежного сервиса (клиент кидает запрос в платежный сервис, платежный
    // сервис запрашивает продукты клиента у сервиса продуктов и возвращает клиенту результат)"
    @GetMapping("/products")
    public List<ResposeProductDto> getUserProducts(@RequestParam Long userId) {
        return payProductServise.getUserProducts(userId);
    }

    // ТЗ: Добавить в процесс исполнения платежа выбор продукта, проверку его существования и достаточности средств на нем
    @PostMapping(value = "/dopay", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentResponseDto doPayment(@RequestBody PaymentDto paymentDto) {
        return payProductServise.doPayment(paymentDto).getBody();
    }

    // предварительная проверка на доступность тек. остатка сумме платежа:
    // при успешной проверке вернем тек. остаток + номер счета
    // при неуспешной проверке вызовем exception со статусом EXPECTATION_FAILED + вернем в сообщении тек. остаток + номер счета
    @PostMapping(value = "/check", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponseDto> checkPay(@RequestBody PaymentDto paymentDto) {
        return payProductServise.checkPay(paymentDto);
    }
}
