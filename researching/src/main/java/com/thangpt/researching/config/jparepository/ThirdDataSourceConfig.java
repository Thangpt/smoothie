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
@EnableJpaRepositories(basePackages = {"com.thangpt.researching.feature.customize"},
        entityManagerFactoryRef = "thirdEntityManagerFactory",
        transactionManagerRef = "thirdTransactionManager")
public class ThirdDataSourceConfig {

    @Bean(name = "thirdDataSourceProperties")
    @ConfigurationProperties("spring.third-datasource")
    public DataSourceProperties thirdDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "thirdJpaProperties")
    @ConfigurationProperties("spring.third-jpa-datasource.jpa")
    public JpaProperties thirdJpaProperties() {
        return new JpaProperties();
    }

    @Bean(name = "thirdDataSource")
    @ConfigurationProperties("spring.third-datasource.hikari")
    public DataSource thirdDataSource(
            @Qualifier("thirdDataSourceProperties") DataSourceProperties thirdDataSourceProperties) {
        final HikariDataSource dataSource = thirdDataSourceProperties
                .initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
        dataSource.setPoolName(thirdDataSourceProperties.getName());
        return dataSource;
    }

    @Bean(name = "thirdEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean boEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("thirdDataSource") DataSource thirdDataSource,
            @Qualifier("thirdJpaProperties") JpaProperties thirdJpaProperties) {
        return builder.dataSource(thirdDataSource)
                .packages("com.thangpt.researching.feature.customize")
                .persistenceUnit("third")
                .properties(thirdJpaProperties.getProperties())
                .build();
    }

    @Bean(name = "thirdTransactionManager")
    public PlatformTransactionManager thirdTransactionManager(
            @Qualifier("thirdEntityManagerFactory") EntityManagerFactory thirdEntityManagerFactory) {
        return new JpaTransactionManager(thirdEntityManagerFactory);
    }
}