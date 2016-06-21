package com.mvc.demo;

import java.io.IOException;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.mvc.demo")
public class Config {
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() { 
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean(); 
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.mvc.demo.model.entity" }); 
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); 
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em; 
	}

	@Bean
	public DataSource dataSource() { 
		DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
		dataSource.setUsername("postgres");
		dataSource.setPassword("123");
		return dataSource; 
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) { 
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() { 
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	Properties additionalProperties() { 
		Properties properties = new Properties(); 
		properties.setProperty("hibernate.hbm2ddl.auto", "validate");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.order_inserts", "true");
		properties.setProperty("hibernate.order_updates", "true");
		properties.setProperty("hibernate.jdbc.batch_size", "2"); 
		return properties;
	}

	
}
