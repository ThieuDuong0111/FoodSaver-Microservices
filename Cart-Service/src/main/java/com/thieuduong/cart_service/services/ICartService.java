package com.thieuduong.cart_service.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import com.thieuduong.cart_service.models.Cart;
import com.thieuduong.cart_service.models.CartItem;
import com.thieuduong.commons.dto.CartDTO;
import com.thieuduong.commons.dto.CartItemDTO;
import com.thieuduong.commons.dto.CartItemProductDTO;
import com.thieuduong.commons.dto.OrderDTO;
import com.thieuduong.commons.dto.ProductDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICartService {

	Mono<CartDTO> convertToDto(Cart cart);

	CartItemDTO convertoCartItemDTO(CartItem cartItem, ProductDTO productDTO);

//	OrderDetail convertToOrderDetail(CartItem cartItem);

	Mono<CartDTO> getItems(Integer userId);

	Mono<CartDTO> updateItem(Integer userId, CartItemProductDTO cartItemProductDTO);

	Mono<CartDTO> deleteItem(Integer userId, CartItemDTO cartItemDTO);

	Mono<CartDTO> checkout(Integer userId) throws IllegalArgumentException, ParseException;

	Flux<OrderDTO> completeOrder(Integer userId, OrderDTO orderDTO) throws ParseException;

	BigDecimal calculateTotalAmountOfCart(List<CartItem> cartItems);

//	BigDecimal calculateTotalAmountOfOrder(Order order);

	Mono<Void> seperateCartItemsByCreator(List<CartItem> cartItems, CartDTO cartDTO);
}
