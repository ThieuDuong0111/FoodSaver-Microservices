package com.thieuduong.product_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.product_service.services.ProductServiceImpl;

@RestController
@RequestMapping("/api/search")
public class SearchController {

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@GetMapping
	public ResponseEntity<?> getProductByName(@RequestParam(value = "productName") String name) {
		return ResponseEntity.ok(productServiceImpl.searchByName(name));
	}
}
