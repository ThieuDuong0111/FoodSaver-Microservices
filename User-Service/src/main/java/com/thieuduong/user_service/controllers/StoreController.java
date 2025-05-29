package com.thieuduong.user_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.commons.dto.UserDTO;
import com.thieuduong.user_service.services.UserServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class StoreController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@GetMapping({ "/store/{id}" })
	public Mono<ResponseEntity<UserDTO>> getUserDetail(@PathVariable int id) {
		return userServiceImpl.getUserById(id).map(userServiceImpl::convertToDto).map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@GetMapping({ "/store/all" })
	public Mono<ResponseEntity<Flux<UserDTO>>> getAllStores() {
		return Mono.just(ResponseEntity.ok().body(userServiceImpl.getAllStores()));
	}

	@GetMapping({ "/store/get-10-newest-stores" })
	public Mono<ResponseEntity<Flux<UserDTO>>> get10NewestStore() {
		return Mono.just(ResponseEntity.ok().body(userServiceImpl.get10NewestStore()));
	}
}
