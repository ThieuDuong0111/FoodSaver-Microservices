package com.thieuduong.category_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thieuduong.category_service.services.CategoryServiceImpl;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl categoryServiceImpl;

	@GetMapping({ "/categories/all" })
	public ResponseEntity<?> getAllProducts() {
		return ResponseEntity.ok(categoryServiceImpl.getAllCategories());
	}
}
