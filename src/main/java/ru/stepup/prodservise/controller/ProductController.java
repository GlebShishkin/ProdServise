package ru.stepup.prodservise.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepup.prodservise.dto.PaymentDto;
import ru.stepup.prodservise.dto.PaymentResponseDto;
import ru.stepup.prodservise.dto.ProductDto;
import ru.stepup.prodservise.entity.Product;
import ru.stepup.prodservise.exceptions.NotFoundException;
import ru.stepup.prodservise.service.ProductService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ProductDto getProductById(@RequestParam Long id) throws SQLException {
        Product product = productService.getProduct(id).orElseThrow(() -> new NotFoundException("Продукт с id = " + id + " не найден", HttpStatus.NOT_FOUND));
        return new ProductDto(product.getId(), product.getUserid(), product.getAccount(), product.getSaldo(), product.getTyp());
    }

    @GetMapping("/products")
    public List<ProductDto> getUserProduct(@RequestParam Long userId) {
          return productService.getProducts(userId).stream().map(x -> new ProductDto(x.getId(), x.getUserid(), x.getAccount(), x.getSaldo(), x.getTyp())).collect(Collectors.toList());
    }

    @PostMapping(value = "/dopay", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentResponseDto doPayment(@RequestBody PaymentDto payDto) throws SQLException {
        return productService.doPaymet(payDto.prodId(), payDto.saldo());
    }

    @PostMapping(value = "/check", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentResponseDto> checkPay(@RequestBody PaymentDto payDto) throws SQLException {
        return productService.checkPay(payDto.prodId(), payDto.saldo());
    }

}
