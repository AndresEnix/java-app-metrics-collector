package com.andres.metrics.collector.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DbConfig {
    @Bean
    public Connection connection(@Autowired DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }
}
