//package com.thieuduong.api_gateway;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity.FormLoginSpec;
//import org.springframework.security.config.web.server.ServerHttpSecurity.HttpBasicSpec;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//import com.thieuduong.api_gateway.security.JwtAuthenticationEntryPoint;
//import com.thieuduong.api_gateway.security.JwtAuthenticationFilter;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//	@Autowired
//	private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//	@Autowired
//	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//	@Bean
//	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
////		return http.csrf(csrf -> csrf.disable())
////				.exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
////				.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec.pathMatchers("/").permitAll()
////						.pathMatchers("/api/sign-in/**").permitAll().pathMatchers("/api/sign-up/**").permitAll()
////						.pathMatchers("/api/banner/**").permitAll().pathMatchers("/api/banners/**").permitAll()
////						.pathMatchers("/api/home/**").permitAll().pathMatchers("/api/store/**").permitAll()
////						.pathMatchers("/api/search/**").permitAll().pathMatchers("/api/category/**").permitAll()
////						.pathMatchers("/api/categories/**").permitAll().pathMatchers("/api/product/**").permitAll()
////						.pathMatchers("/api/products/**").permitAll().pathMatchers("/api/image/**").permitAll()
////						.pathMatchers("/api/feedback/get-feedbacks/**").permitAll().anyExchange().authenticated())
////				.addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
////				.httpBasic(httpBasic -> httpBasic.disable()).formLogin(formLogin -> formLogin.disable()).build();
//
//		return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
//				.authorizeExchange(exchange -> exchange.anyExchange().permitAll())
//				.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//				.formLogin(ServerHttpSecurity.FormLoginSpec::disable).build();
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
////	@Bean
////	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
////		return config.getAuthenticationManager();
////	}
//
//}