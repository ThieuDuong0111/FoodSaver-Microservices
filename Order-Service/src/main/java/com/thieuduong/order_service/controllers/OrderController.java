package com.thieuduong.order_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.thieuduong.commons.dto.OrderDTO;
import com.thieuduong.order_service.services.OrderServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderServiceImpl orderServiceImpl;

	@GetMapping({ "/order-history" })
	public Mono<ResponseEntity<Flux<OrderDTO>>> getOrderByUserId(ServerWebExchange exchange) {
		Integer id = Integer.valueOf(exchange.getRequest().getHeaders().getFirst("id"));
		return Mono.just(ResponseEntity.ok().body(orderServiceImpl.getOrdersByUserId(id)));
	}

	@GetMapping({ "/order-detail/{id}" })
	public Mono<ResponseEntity<OrderDTO>> getOrderDetail(@PathVariable int id) {
		return orderServiceImpl.getOrderById(id).map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

}
