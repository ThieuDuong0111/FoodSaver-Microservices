package com.thieuduong.product_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.product_service.services.CategoryServiceImpl;
import com.thieuduong.commons.dto.CategoryDTO;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	private CategoryServiceImpl categoryServiceImpl;

	@GetMapping({ "/categories/all" })
	public ResponseEntity<?> getAllCategorys() {
		return ResponseEntity.ok(categoryServiceImpl.getAllCategories());
	}

	@GetMapping({ "/category/{id}" })
	public ResponseEntity<?> getCategoryDetail(@PathVariable int id) {
		CategoryDTO CategoryDTO = categoryServiceImpl.convertToDto(categoryServiceImpl.getCategoryById(id));
		if (CategoryDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(CategoryDTO);
	}
}
