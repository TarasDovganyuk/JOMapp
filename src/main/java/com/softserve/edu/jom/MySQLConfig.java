package com.softserve.edu.jom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:local.db.properties")
public class MySQLConfig {
    private final Environment env;

    @Autowired
    public MySQLConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .driverClassName(env.getRequiredProperty("datasource.driverClassName"))
                .url(env.getRequiredProperty("datasource.url"))
                .username(env.getRequiredProperty("datasource.username"))
                .password(env.getRequiredProperty("datasource.password"))
                .build();
    }
}
