package com.thieuduong.order_service.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http.csrf(csrf -> csrf.disable())
				.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec.anyExchange().permitAll()).build();
	}
}
