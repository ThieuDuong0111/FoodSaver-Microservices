//package com.thieuduong.cart_service.services;
//
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.util.List;
//
//import com.funix.foodsaverAPI.dto.CartDTO;
//import com.funix.foodsaverAPI.dto.CartItemDTO;
//import com.funix.foodsaverAPI.dto.CartItemProductDTO;
//import com.funix.foodsaverAPI.dto.OrderDTO;
//import com.funix.foodsaverAPI.models.Cart;
//import com.funix.foodsaverAPI.models.CartItem;
//import com.funix.foodsaverAPI.models.Order;
//import com.funix.foodsaverAPI.models.OrderDetail;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//public interface ICartService {
//
//	CartDTO convertToDto(Cart cart);
//
//	OrderDetail convertToOrderDetail(CartItem cartItem);
//
//	CartDTO getItems(HttpServletRequest request);
//
//	CartDTO updateItem(HttpServletRequest request,
//		CartItemProductDTO cartItemProductDTO);
//
//	CartDTO deleteItem(HttpServletRequest request,
//		CartItemDTO cartItemDTO);
//
//	CartDTO checkout(HttpServletRequest request)
//		throws IllegalArgumentException, ParseException;
//
//	List<OrderDTO> completeOrder(OrderDTO orderDTO, HttpServletRequest request) throws ParseException;
//
//	BigDecimal calculateTotalAmountOfCart(Cart cart);
//
//	BigDecimal calculateTotalAmountOfOrder(Order order);
//
//	void seperateCartItemsByCreator(Cart cart, CartDTO cartDTO);
//}
