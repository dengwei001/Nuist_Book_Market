package com.nuist.bookMarket.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Created by lenovo on 2017/8/10.
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    Environment env;

    @Bean("dataSource")
    public DataSource dataSource(){

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(env.getProperty("boot.datasource.driverClassName","com.mysql.jdbc.Driver"));
        dataSource.setUrl(env.getProperty("boot.datasource.url"));
        dataSource.setUsername(env.getProperty("boot.datasource.username"));
        dataSource.setPassword(env.getProperty("boot.datasource.password"));
        dataSource.setValidationQuery(env.getProperty("boot.datasource.ValidationQuery"));
        dataSource.setMaxIdle(Integer.parseInt(env.getProperty("boot.datasource.maxIdle")));
        dataSource.setMinIdle(Integer.parseInt(env.getProperty("boot.datasource.minIdle")));
        dataSource.setMaxActive(Integer.parseInt(env.getProperty("boot.datasource.maxActive")));
        dataSource.setInitialSize(Integer.parseInt(env.getProperty("boot.datasource.initialSize")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("boot.datasource.testOnBorrow")));
        dataSource.setTestOnReturn(Boolean.parseBoolean(env.getProperty("boot.datasource.testOnReturn")));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("boot.datasource.testWhileIdel")));
        dataSource.setLogAbandoned(Boolean.parseBoolean(env.getProperty("boot.datasource.logAbandoned")));
        dataSource.setTimeBetweenEvictionRunsMillis(Integer.parseInt(env.getProperty("boot.datasource.timeBetweenEvictionRunsMillis")));
        dataSource.setMinEvictableIdleTimeMillis(Integer.parseInt(env.getProperty("boot.datasource.minEvictableIdleTimeMillis")));
        dataSource.setRemoveAbandoned(Boolean.parseBoolean(env.getProperty("boot.datasource.removeAbandoned")));

        return dataSource;
    }


}
