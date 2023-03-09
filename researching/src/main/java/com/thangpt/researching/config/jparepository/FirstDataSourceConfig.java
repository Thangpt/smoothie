package com.thangpt.researching.config.jparepository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.thangpt.researching.feature.biz"},
        entityManagerFactoryRef = "firstEntityManagerFactory",
        transactionManagerRef = "firstTransactionManager")
public class FirstDataSourceConfig {

    @Primary
    @Bean(name = "firstDataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "firstJpaProperties")
    @ConfigurationProperties("spring.first-jpa-datasource.jpa")
    public JpaProperties firstJpaProperties() {
        return new JpaProperties();
    }

    @Primary
    @Bean(name = "firstDataSource")
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource firstDataSource(
            @Qualifier("firstDataSourceProperties") DataSourceProperties firstDataSourceProperties) {
        final HikariDataSource dataSource = firstDataSourceProperties
                .initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
        dataSource.setPoolName(firstDataSourceProperties.getName());
        return dataSource;
    }

    @Primary
    @Bean(name = "firstEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean boEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("firstDataSource") DataSource firstDataSource,
            @Qualifier("firstJpaProperties") JpaProperties firstJpaProperties) {
        return builder.dataSource(firstDataSource)
                .packages("com.thangpt.researching.feature.biz")
                .persistenceUnit("first")
                .properties(firstJpaProperties.getProperties())
                .build();
    }

    @Primary
    @Bean(name = "firstTransactionManager")
    public PlatformTransactionManager firstTransactionManager(
            @Qualifier("firstEntityManagerFactory") EntityManagerFactory firstEntityManagerFactory) {
        return new JpaTransactionManager(firstEntityManagerFactory);
    }
}