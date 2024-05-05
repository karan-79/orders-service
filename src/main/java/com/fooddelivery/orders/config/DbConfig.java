package com.fooddelivery.orders.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Bean
    public DataSource dataSource() {
        var ds = (HikariDataSource) DataSourceBuilder.create()
                .url("jdbc:hsqldb:mem:testdb")
                .username("sa")
                .password("")
                .build();
        System.out.println("Initialized DS" +  ds.getDriverClassName());
        return ds;
    }
}
