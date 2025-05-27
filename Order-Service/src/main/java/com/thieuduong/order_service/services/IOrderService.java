package com.thieuduong.order_service.services;

import java.util.List;

import com.thieuduong.commons.dto.OrderDTO;
import com.thieuduong.order_service.models.Order;

public interface IOrderService {

	OrderDTO convertToDto(Order order);

	Order convertToEntity(OrderDTO orderDTO);

//	List<OrderDTO> getOrderByUserId(HttpServletRequest request);

	OrderDTO getOrderById(int id);
}
