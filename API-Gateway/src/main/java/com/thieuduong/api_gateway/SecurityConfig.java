package com.thieuduong.api_gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("user").password(passwordEncoder().encode("password")).roles("USER")
				.build();
		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
				.build();
		return new MapReactiveUserDetailsService(user, admin);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(exchange -> exchange.pathMatchers("/api/resource/**").authenticated() // Yêu cầu xác thực
																										// cho route
																										// /api/resource/**
				.anyExchange().permitAll() // Cho phép truy cập tất cả các route khác
		).httpBasic(Customizer.withDefaults()) // Sử dụng Basic Authentication
				.csrf(ServerHttpSecurity.CsrfSpec::disable); // Vô hiệu hóa CSRF cho mục đích ví dụ
		return http.build();
	}
}