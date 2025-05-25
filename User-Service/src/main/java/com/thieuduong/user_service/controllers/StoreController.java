package com.thieuduong.user_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.user_service.services.UserServiceImpl;

@RestController
@RequestMapping("/api")
public class StoreController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@GetMapping({ "/store/all" })
	public ResponseEntity<?> getAllStores() {
		return ResponseEntity.ok(userServiceImpl.getAllStores());
	}

	@GetMapping({ "/store/get-10-newest-stores" })
	public ResponseEntity<?> get10NewestStore() {
		return ResponseEntity.ok(userServiceImpl.get10NewestStore());
	}
}
