package com.thieuduong.order_service.services;

import com.thieuduong.commons.dto.OrderDTO;
import com.thieuduong.order_service.models.Order;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IOrderService {

	Mono<OrderDTO> convertToDto(Order order);

	Flux<OrderDTO> getOrdersByUserId(int userId);

	Mono<OrderDTO> getOrderById(int id);
}
