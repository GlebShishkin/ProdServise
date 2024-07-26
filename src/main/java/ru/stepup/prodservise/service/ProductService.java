package ru.stepup.prodservise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.stepup.prodservise.dao.ProductDao;
import ru.stepup.prodservise.dto.PaymentResponseDto;
import ru.stepup.prodservise.entity.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductDao productDao;

    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public long save (Product product) {
        return productDao.save(product);
    }

    public List<Product> getProducts(Long userId) {
        return productDao.getUserProducts(userId);
    }

    public Optional<Product> getProduct(Long id)  throws SQLException {
        return Optional.ofNullable(productDao.getProduct(id));
    }

    public PaymentResponseDto doPaymet(Long prodId, BigDecimal saldo) throws SQLException {
            return productDao.doPay(prodId, saldo);
    }

    public ResponseEntity<PaymentResponseDto> checkPay(Long prodId, BigDecimal saldo) throws SQLException {
        return productDao.checkPay(prodId, saldo);
    }


    public void deleteAll() {
        productDao.deleteAll();
    }
}
