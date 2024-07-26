package ru.stepup.prodservise;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Getter
@Component("JdbcConnection")
public class JdbcConnection {

    private Connection connection;

    public DataSource dataSource() {

        HikariConfig config = new HikariConfig();
        final HikariDataSource dataSource;

        config.setJdbcUrl("jdbc:postgresql://localhost:5432/hikari");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
        return dataSource;
    }

    public JdbcConnection() {
        try {
            connection = dataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
