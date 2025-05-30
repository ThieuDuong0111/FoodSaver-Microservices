package com.thieuduong.api_gateway.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.thieuduong.api_gateway.services.AuthServiceImpl;
import com.thieuduong.api_gateway.services.MyUserDetails;
import com.thieuduong.commons.utils.JWTUtils;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements WebFilter {

	@Autowired
	private AuthServiceImpl authServiceImpl;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		// Create a new ServerWebExchange with the modified request
		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		String extractedToken = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			extractedToken = authHeader.substring(7);
			username = JWTUtils.extractUsername(extractedToken);
		}
		final String token = extractedToken;
		if (username != null) {
			return authServiceImpl.findByUsername(username) // Mono<MyUserDetails>
					.filter(myUserDetails -> validateToken(token, myUserDetails))
					.map(myUserDetails -> new UsernamePasswordAuthenticationToken(myUserDetails, null,
							myUserDetails.getAuthorities()))
					.flatMap(auth -> {
						MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
						String userId = String.valueOf(myUserDetails.getId());
						ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().header("id", userId)
								.header("name", myUserDetails.getUsername()).build();

						ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();

						return chain.filter(modifiedExchange)
								.contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
					});
//			return authServiceImpl.findByUsername(username) // Mono<MyUserDetails>
//					.filter(myUserDetails -> validateToken(token, myUserDetails))
//					.map(myUserDetails -> new UsernamePasswordAuthenticationToken(myUserDetails, null,
//							myUserDetails.getAuthorities()))
//					.flatMap(auth -> {
//						ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//								.header("name", auth.getName()).build();
//						ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
//						return chain.filter(modifiedExchange)
//								.contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
//					});
		}
		return chain.filter(exchange);
	}

	private Boolean isTokenExpired(String token) {
		return JWTUtils.extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, MyUserDetails myUserDetails) {
		final String username = JWTUtils.extractUsername(token);
		return (username.equals(myUserDetails.getUsername()) && !isTokenExpired(token));
	}

}
