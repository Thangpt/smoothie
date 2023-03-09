package com.thangpt.researching.config.jparepository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.thangpt.researching.feature.tech"},
        entityManagerFactoryRef = "secondEntityManagerFactory",
        transactionManagerRef = "secondTransactionManager")
public class SecondDataSourceConfig {

    @Bean(name = "secondDataSourceProperties")
    @ConfigurationProperties("spring.second-datasource")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "secondJpaProperties")
    @ConfigurationProperties("spring.second-jpa-datasource.jpa")
    public JpaProperties secondJpaProperties() {
        return new JpaProperties();
    }

    @Bean(name = "secondDataSource")
    @ConfigurationProperties("spring.second-datasource.hikari")
    public DataSource secondDataSource(
            @Qualifier("secondDataSourceProperties") DataSourceProperties secondDataSourceProperties) {
        final HikariDataSource dataSource = secondDataSourceProperties
                .initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
        dataSource.setPoolName(secondDataSourceProperties.getName());
        return dataSource;
    }

    @Bean(name = "secondEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean boEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("secondDataSource") DataSource secondDataSource,
            @Qualifier("secondJpaProperties") JpaProperties secondJpaProperties) {
        return builder.dataSource(secondDataSource)
                .packages("com.thangpt.researching.feature.tech")
                .persistenceUnit("second")
                .properties(secondJpaProperties.getProperties())
                .build();
    }

    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager secondTransactionManager(
            @Qualifier("secondEntityManagerFactory") EntityManagerFactory secondEntityManagerFactory) {
        return new JpaTransactionManager(secondEntityManagerFactory);
    }
}