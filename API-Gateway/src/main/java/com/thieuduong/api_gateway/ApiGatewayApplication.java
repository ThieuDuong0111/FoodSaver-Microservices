package com.thieuduong.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes().route("product-service", r -> r.path("/api/products/**").uri("http://product-service"))
				.route("user-service", r -> r.path("/api/users/**").uri("http://user-service"))
				.route("order-service", r -> r.path("/api/orders/**").uri("http://order-service"))
//				.route("host_route", r -> r.host("*.myhost.org").uri("https://httpbin.org"))
//				.route("rewrite_route",
//						r -> r.host("*.rewrite.org").filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
//								.uri("https://httpbin.org"))
//				.route("circuit_breaker_route",
//						r -> r.host("*.circuitbreaker.org").filters(f -> f.circuitBreaker(c -> c.setName("slowcmd")))
//								.uri("https://httpbin.org"))
//				.route("circuit_breaker_fallback_route",
//						r -> r.host("*.circuitbreakerfallback.org")
//								.filters(f -> f.circuitBreaker(
//										c -> c.setName("slowcmd").setFallbackUri("forward:/circuitbrekerfallback")))
//								.uri("https://httpbin.org"))
//				.route("limit_route",
//						r -> r.host("*.limited.org").and().path("/anything/**")
//								.filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
//								.uri("https://httpbin.org"))
				.build();
	}
}
