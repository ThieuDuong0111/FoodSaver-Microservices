package com.thieuduong.order_service.services;

import java.math.BigDecimal;
import java.util.List;

import com.thieuduong.commons.dto.CompleteOrderDTO;
import com.thieuduong.commons.dto.OrderDTO;
import com.thieuduong.order_service.models.Order;
import com.thieuduong.order_service.models.OrderDetail;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IOrderService {

	Mono<OrderDTO> convertToDto(Order order);

	Flux<OrderDTO> getOrdersByUserId(int userId);

	Mono<OrderDTO> getOrderById(int id);

	Flux<OrderDTO> completeOrder(CompleteOrderDTO completeOrderDTO);

	BigDecimal calculateTotalAmountOfOrder(List<OrderDetail> orderDetails);
}
