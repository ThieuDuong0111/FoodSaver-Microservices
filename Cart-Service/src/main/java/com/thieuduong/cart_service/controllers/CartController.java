package com.thieuduong.cart_service.controllers;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.thieuduong.cart_service.services.CartServiceImpl;
import com.thieuduong.commons.dto.CartDTO;
import com.thieuduong.commons.dto.CartItemDTO;
import com.thieuduong.commons.dto.CartItemProductDTO;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartServiceImpl cartServiceImpl;

	@GetMapping("/get-items")
	public Mono<ResponseEntity<CartDTO>> getItems(ServerWebExchange exchange) {
		Integer id = Integer.valueOf(exchange.getRequest().getHeaders().getFirst("id"));
		return cartServiceImpl.getItems(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@PostMapping("/update-item")
	public Mono<ResponseEntity<CartDTO>> updateItem(ServerWebExchange exchange,
			@RequestBody CartItemProductDTO cartItemProductDTO) {
		Integer id = Integer.valueOf(exchange.getRequest().getHeaders().getFirst("id"));
		return cartServiceImpl.updateItem(id, cartItemProductDTO).map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/delete-item")
	public Mono<ResponseEntity<CartDTO>> deleteItem(ServerWebExchange exchange, @RequestBody CartItemDTO cartItemDTO) {
		Integer id = Integer.valueOf(exchange.getRequest().getHeaders().getFirst("id"));
		return cartServiceImpl.deleteItem(id, cartItemDTO).map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@PostMapping("/checkout")
	public Mono<ResponseEntity<CartDTO>> checkout(ServerWebExchange exchange)
			throws IllegalArgumentException, ParseException {
		Integer id = Integer.valueOf(exchange.getRequest().getHeaders().getFirst("id"));
		return cartServiceImpl.checkout(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().build());
	}
//
//	@PostMapping("/complete-order")
//	public ResponseEntity<?> completeOrder(@RequestBody OrderDTO orderDTO, HttpServletRequest request)
//			throws ParseException {
//		try {
//			return ResponseEntity.ok(cartServiceImpl.completeOrder(orderDTO, request));
//		} catch (IllegalArgumentException e) {
//			return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
//		}
//	}
}
