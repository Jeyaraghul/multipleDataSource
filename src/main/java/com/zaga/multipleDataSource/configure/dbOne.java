package com.zaga.multipleDataSource.configure;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(transactionManagerRef = "taxiTransactionManager", entityManagerFactoryRef = "taxiEntityManagerFactory", basePackages = {
        "com.zaga.multipleDataSource.taxiBooking" })
public class dbOne {

    @Bean(name = "taxiDataSourceprops")
    @ConfigurationProperties("taxi.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "taxiDataSource")
    @ConfigurationProperties("taxi.datasource")
    public DataSource dataSource(@Qualifier("taxiDataSourceprops") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "taxiEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean taxiEntityManagerFactoryBean(EntityManagerFactoryBuilder builder,
            @Qualifier("taxiDataSource") DataSource taxiDataSource) {
        return builder.dataSource(taxiDataSource).packages("com.zaga.multipleDataSource.taxiBooking")
                .persistenceUnit("taxi").build();

    }

    @Bean(name = "taxiTransactionManager")
    @ConfigurationProperties("taxi.jpa")
    public PlatformTransactionManager taxiTransactionManager(
            @Qualifier("taxiEntityManagerFactory") EntityManagerFactory taxiEntityManagerFactory) {
        return new JpaTransactionManager(taxiEntityManagerFactory);
    }

}
