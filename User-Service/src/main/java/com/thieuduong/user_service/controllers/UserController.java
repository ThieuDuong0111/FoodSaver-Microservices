package com.thieuduong.user_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.thieuduong.commons.dto.UserDTO;
import com.thieuduong.user_service.services.UserServiceImpl;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@GetMapping("/user-info")
	public Mono<ResponseEntity<UserDTO>> getUserInfo(ServerWebExchange exchange) {
		String name = exchange.getRequest().getHeaders().getFirst("name");
		Integer id = Integer.valueOf(exchange.getRequest().getHeaders().getFirst("id"));
		if (name == null || name.isEmpty()) {
			return Mono.just(ResponseEntity.badRequest().build());
		}
		return userServiceImpl.getUserById(id).map(userServiceImpl::convertToDto)
				.map(userDTO -> ResponseEntity.ok(userDTO)).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PutMapping(path = "/update-info", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Mono<ResponseEntity<UserDTO>> updateUserInfo(ServerWebExchange exchange, @ModelAttribute UserDTO userDTO) {
		return userServiceImpl.updateUserInfoMobile(exchange, userDTO).map(user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.notFound().build());

	}

}
