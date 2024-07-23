package ru.stepup.payservise.servise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.stepup.payservise.config.properties.ApplicationProperties;
import ru.stepup.payservise.dto.PaymentDto;
import ru.stepup.payservise.dto.ResposeProductDto;
import ru.stepup.prodservise.dto.PaymentResponseDto;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class PayProductServise {

    private RestTemplate restTemplate;
    private static ApplicationProperties applicationProperties;

    @Autowired
    public PayProductServise(RestTemplate restTemplate, ApplicationProperties applicationProperties) {
        this.restTemplate = restTemplate;
        this.applicationProperties = applicationProperties;
    }

    // запрос продуктов пользователя
    public List<ResposeProductDto> getUserProducts(Long userId) {

        ResponseEntity<ResposeProductDto[]> response =
                restTemplate.getForEntity(
                        this.applicationProperties.getUrl() + "/api/products?userId=" + userId,
                        ResposeProductDto[].class);

        return Arrays.asList(response.getBody());
    }

    public ResponseEntity<PaymentResponseDto> doPayment(PaymentDto paymentDto)  {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto, headers);

        return restTemplate.postForEntity(this.applicationProperties.getUrl() + "/api/dopay", request, PaymentResponseDto.class);
    }

}
