package com.thieuduong.product_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thieuduong.commons.dto.ProductDTO;
import com.thieuduong.commons.dto.ProductOrderDTO;
import com.thieuduong.product_service.services.ProductServiceImpl;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@GetMapping({ "/products/all" })
	public ResponseEntity<?> getAllProducts() {
		return ResponseEntity.ok(productServiceImpl.getAllProducts());
	}

	@GetMapping({ "/product/{id}" })
	public ResponseEntity<?> getProductDetail(@PathVariable("id") int integer) {
		ProductDTO productDTO = productServiceImpl.getProductById(integer);
		if (productDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productDTO);
	}

	@GetMapping({ "/products/by-category/{id}" })
	public ResponseEntity<?> getProductByCategoryId(@PathVariable("id") int integer) {
		List<ProductDTO> products = productServiceImpl.findByCategoryId(integer);
		return ResponseEntity.ok(products);
	}

	@GetMapping({ "/products/by-store/{id}" })
	public ResponseEntity<?> getProductByStoreId(@PathVariable int id) {
		return ResponseEntity.ok(productServiceImpl.findByStoreId(id));
	}

	@GetMapping({ "/product/update-product-after-complete-order" })
	public ResponseEntity<?> updateProductAfterCompleteOrder(@RequestParam("product-id") Integer id,
			@RequestParam("product-quantity") int quantity, @RequestParam("product-soldcount") int soldCount) {
		productServiceImpl.updateProduct(id, quantity, soldCount);
		return ResponseEntity.ok("Product updated successfully.");
	}

	@GetMapping({ "/product-order/{id}" })
	public ResponseEntity<?> getProductOrderDetail(@PathVariable("id") int integer) {
		ProductOrderDTO productOrderDTO = productServiceImpl.getProductOrderById(integer);
		if (productOrderDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productOrderDTO);
	}

}
