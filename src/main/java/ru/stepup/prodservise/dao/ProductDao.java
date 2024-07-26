package ru.stepup.prodservise.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.stepup.prodservise.JdbcConnection;
import ru.stepup.prodservise.dto.PaymentResponseDto;
import ru.stepup.prodservise.entity.Product;
import ru.stepup.prodservise.enumerator.ProdType;
import ru.stepup.prodservise.exceptions.InsufficientFundsException;
import ru.stepup.prodservise.exceptions.NotFoundException;
import ru.stepup.prodservise.exceptions.UniException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
public class ProductDao {
    private final Connection connection;
    @Autowired
    public ProductDao(JdbcConnection jdbcConnection) {
        this.connection = jdbcConnection.getConnection();
    }

    public long save (Product product) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO products(userid, account, saldo, typ) VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, product.getUserid());
            preparedStatement.setString(2, product.getAccount());
            preparedStatement.setBigDecimal(3, product.getSaldo());
            preparedStatement.setString(4, product.getTyp().name());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                var rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    product.setId(rs.getLong(1));
                    return rs.getLong(1);   // вернем id нового продукта
                }
            }
        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    //  ТЗ Добавить возможность запрашивать продукты у платежного сервиса (клиент кидает запрос в платежный сервис,
    //  платежный сервис запрашивает продукты клиента у сервиса продуктов и возвращает клиенту результат)
    public List<Product> getUserProducts(Long userId) {

        List<Product> listProduct = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM products t where t.userid = " + userId;
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setUserid(resultSet.getLong("userid"));
                product.setAccount(resultSet.getString("account"));
                product.setSaldo(resultSet.getBigDecimal("saldo"));
                product.setTyp(ProdType.valueOf(resultSet.getString("typ")));
                listProduct.add(product);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return listProduct;
    }

    // запрос продукта по id
    public Product getProduct(Long prodId)  throws SQLException {
        Product product = null;

        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM products WHERE id = ?");
        preparedStatement.setLong(1, prodId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next())
        {
            product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setUserid(resultSet.getLong("userid"));
            product.setAccount(resultSet.getString("account"));
            product.setSaldo(resultSet.getBigDecimal("saldo"));
            product.setTyp(ProdType.valueOf(resultSet.getString("typ")));
            return product;
        }
        else {
            throw new NotFoundException("По id = " + prodId + " продукт не найден", HttpStatus.NOT_FOUND);
        }
    }

    // ТЗ: "Добавить в процесс исполнения платежа выбор продукта, проверку его существования и достаточности средств на нем
    // проверка достаточности средств + совершение платежа (изменение остатка)"
    // совершение платежа по id продукта + сумме платежа
    public PaymentResponseDto doPay(Long prodId, BigDecimal saldo) throws SQLException {

            Product product = getProduct(prodId);
            if (product.getSaldo().compareTo(saldo) < 0) {
                throw new InsufficientFundsException("Недостаточно средств на счете " + product.getAccount(), HttpStatus.EXPECTATION_FAILED);
            }

            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE products SET saldo = ? WHERE id = ?");
            preparedStatement.setBigDecimal(1, product.getSaldo().subtract(saldo));
            preparedStatement.setLong(2, prodId);
            if (preparedStatement.executeUpdate() == 0) {
                throw new UniException("По id = " + prodId + " продукт не найден", HttpStatus.NOT_FOUND);
            }

            // вернем текущий остаток по счету после совершения платежа
            return new PaymentResponseDto(product.getAccount(), product.getSaldo().subtract(saldo));
    }

    // проверка достаточности средств на счете продукта (запускается отдельным запросом со стороны сервиса платежей)
    public ResponseEntity<PaymentResponseDto> checkPay(Long prodId, BigDecimal saldo) throws SQLException {

        Product product = getProduct(prodId);
        if (product.getSaldo().compareTo(saldo) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на счете " + product.getAccount(), HttpStatus.EXPECTATION_FAILED);
        }

        // вернем текущий остаток по счету
        return new ResponseEntity<>(new PaymentResponseDto(product.getAccount(), product.getSaldo()), HttpStatus.OK);
    }

    public void deleteAll() {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM products");
            preparedStatement.executeQuery();
        }
        catch (SQLException e) {
        }
    }
}
