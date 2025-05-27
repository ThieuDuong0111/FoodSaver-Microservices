//package com.thieuduong.api_gateway.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//
//import com.thieuduong.api_gateway.services.AuthServiceImpl;
//
//import reactor.core.publisher.Mono;
//
//@Component
//public class JwtAuthenticationFilter implements WebFilter {
//
//	@Autowired
//	private JWTService jWTService;
//
//	@Autowired
//	private AuthServiceImpl authServiceImpl;
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//		// Create a new ServerWebExchange with the modified request
//		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
//		String token = null;
//		String username = null;
//
//		if (authHeader != null && authHeader.startsWith("Bearer ")) {
//			token = authHeader.substring(7);
//			username = jWTService.extractUsername(token);
//		}
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//			UserDetails userDetails = authServiceImpl.loadUserByUsername(username);
//
//			// Processing
//			if (jWTService.validateToken(token, userDetails)) {
//				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
//						null, userDetails.getAuthorities());
//				return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));
//			}
//		}
//		return chain.filter(exchange);
//	}
//
//}
