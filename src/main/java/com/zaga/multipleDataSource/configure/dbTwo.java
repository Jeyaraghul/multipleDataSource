package com.zaga.multipleDataSource.configure;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(transactionManagerRef = "hotelTransactionManager", entityManagerFactoryRef = "hotelEntityManagerFactory", basePackages = {
        "com.zaga.multipleDataSource.hotelBooking" })
public class dbTwo {

    @Primary
    @Bean(name = "hotelDataSourceprops")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "hotelDataSource")
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource(@Qualifier("hotelDataSourceprops") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "hotelEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean hotelEntityManagerFactoryBean(EntityManagerFactoryBuilder builder,
            @Qualifier("hotelDataSource") DataSource hotelDataSource) {
        return builder.dataSource(hotelDataSource).packages("com.zaga.multipleDataSource.hotelBooking")
                .persistenceUnit("hotel").build();

    }

    @Primary
    @Bean(name = "hotelTransactionManager")
    @ConfigurationProperties("spring.jpa")
    public PlatformTransactionManager hotelTransactionManager(
            @Qualifier("hotelEntityManagerFactory") EntityManagerFactory hotelEntityManagerFactory) {
        return new JpaTransactionManager(hotelEntityManagerFactory);
    }

}
