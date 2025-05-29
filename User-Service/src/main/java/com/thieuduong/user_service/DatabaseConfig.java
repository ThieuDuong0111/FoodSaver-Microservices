//package com.thieuduong.user_service;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
//import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
//import org.springframework.r2dbc.connection.R2dbcTransactionManager;
//import org.springframework.transaction.ReactiveTransactionManager;
//
//import io.r2dbc.spi.ConnectionFactory;
//
//@Configuration
//@EnableR2dbcRepositories
//public class DatabaseConfig extends AbstractR2dbcConfiguration {
//
//	@Override
//	@Bean
//	public ConnectionFactory connectionFactory() {
//		return null;
//	}
//
//	@Bean
//	ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
//		return new R2dbcTransactionManager(connectionFactory);
//	}
//}
