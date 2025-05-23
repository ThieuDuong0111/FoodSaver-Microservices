package com.thieuduong.product_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.commons.clients.ICategoryClient;
import com.thieuduong.commons.dto.CategoryDTO;
import com.thieuduong.commons.dto.ProductDTO;
import com.thieuduong.product_service.services.ProductServiceImpl;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@Autowired
	private ICategoryClient categoryClient;

	@GetMapping({ "/products/all" })
	public ResponseEntity<?> getAllProducts() {
		return ResponseEntity.ok(productServiceImpl.getAllProducts());
	}

//	@GetMapping({ "/products/test_getall" })
//	public ResponseEntity<?> testGetAllProducts() {
//		productServiceImpl.testGetAll();
//		return ResponseEntity.ok("Done");
//	}
//
	@GetMapping({ "/product/{id}" })
	public ResponseEntity<?> getProductDetail(@PathVariable("id") int integer) {
		ProductDTO productDTO = productServiceImpl.convertToDto(productServiceImpl.getProductById(integer));
		if (productDTO == null) {
			return ResponseEntity.notFound().build();
		}

		// Gọi Category Service bằng Feign Client, trả về đối tượng Category từ
		// common-models
		CategoryDTO categoryDTO = categoryClient.getCategoryById(productDTO.getCategoryId());

		if (categoryDTO == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		productDTO.setCategory(categoryDTO);
		return ResponseEntity.ok(productDTO);
	}

//
	@GetMapping({ "/products/by-category/{id}" })
	public ResponseEntity<?> getProductByCategoryId(@PathVariable("id") int integer) {
		List<ProductDTO> products = productServiceImpl.findByCategoryId(integer);
		return ResponseEntity.ok(products);
	}
//
//	@GetMapping({ "/products/by-store/{id}" })
//	public ResponseEntity<?> getProductByStoreId(
//		@PathVariable int id) {
//		return ResponseEntity
//			.ok(productServiceImpl.findByStoreId(id));
//	}
}
