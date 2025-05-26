//package com.thieuduong.cart_service.controllers;
//
//import java.text.ParseException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.funix.foodsaverAPI.dto.CartItemDTO;
//import com.funix.foodsaverAPI.dto.CartItemProductDTO;
//import com.funix.foodsaverAPI.dto.ErrorMessageResponse;
//import com.funix.foodsaverAPI.dto.OrderDTO;
//import com.funix.foodsaverAPI.services.CartServiceImpl;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@RestController
//@RequestMapping("/api/cart")
//public class CartController {
//
//	@Autowired
//	private CartServiceImpl cartServiceImpl;
//
//	@GetMapping("/get-items")
//	public ResponseEntity<?> getItems(HttpServletRequest request) {
//		return ResponseEntity.ok(cartServiceImpl.getItems(request));
//	}
//
//	@PostMapping("/update-item")
//	public ResponseEntity<?> updateItem(HttpServletRequest request,
//		@RequestBody CartItemProductDTO cartItemProductDTO) {
//		return ResponseEntity
//			.ok(cartServiceImpl.updateItem(request, cartItemProductDTO));
//	}
//
//	@DeleteMapping("/delete-item")
//	public ResponseEntity<?> deleteItem(HttpServletRequest request,
//		@RequestBody CartItemDTO cartItemDTO) {
//		return ResponseEntity
//			.ok(cartServiceImpl.deleteItem(request, cartItemDTO));
//	}
//
//	@PostMapping("/checkout")
//	public ResponseEntity<?> checkout(HttpServletRequest request)
//		throws ParseException {
//		try {
//			return ResponseEntity
//				.ok(cartServiceImpl.checkout(request));
//		} catch (IllegalArgumentException e) {
//			return new ResponseEntity<>(
//				new ErrorMessageResponse(e.getMessage()),
//				HttpStatus.BAD_REQUEST);
//		}
//	}
//	
//	@PostMapping("/complete-order")
//	public ResponseEntity<?> completeOrder(@RequestBody OrderDTO orderDTO, HttpServletRequest request)
//		throws ParseException {
//		try {
//			return ResponseEntity
//				.ok(cartServiceImpl.completeOrder(orderDTO, request));
//		} catch (IllegalArgumentException e) {
//			return new ResponseEntity<>(
//				new ErrorMessageResponse(e.getMessage()),
//				HttpStatus.BAD_REQUEST);
//		}
//	}
//}
