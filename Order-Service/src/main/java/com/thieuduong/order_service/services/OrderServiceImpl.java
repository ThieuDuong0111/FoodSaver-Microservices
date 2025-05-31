package com.thieuduong.order_service.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.commons.clients.IUserClient;
import com.thieuduong.commons.dto.OrderDTO;
import com.thieuduong.commons.dto.OrderDetailDTO;
import com.thieuduong.commons.utils.ParseUtils;
import com.thieuduong.order_service.models.Order;
import com.thieuduong.order_service.repositories.IOrderDetailRepository;
import com.thieuduong.order_service.repositories.IOrderRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IOrderDetailRepository orderDetailRepository;

	@Autowired
	private IUserClient userClient;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Mono<OrderDTO> convertToDto(Order order) {
		return orderDetailRepository.findAllByOrderId(order.getId())
				.map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDTO.class)).collectList()
				.map(orderDetailDTO -> {
					OrderDTO orderDTO = new OrderDTO();
					orderDTO.setId(order.getId());
					orderDTO.setOrderDetails(orderDetailDTO);
					orderDTO.setPublishedDate(ParseUtils.convertToDate(order.getPublishedDate()));
					orderDTO.setCreator(userClient.getStoreById(order.getCreatorId()));
					orderDTO.setOrderCode(order.getOrderCode());
					orderDTO.setTotalAmount(order.getTotalAmount());
					orderDTO.setCreatorName(order.getCreatorName());
					orderDTO.setStatusType(order.getStatusType());
					orderDTO.setPaymentType(order.getPaymentType());
					orderDTO.setShippingType(order.getShippingType());
					orderDTO.setStatusTypeParse(ParseUtils.convertStatusType(order.getStatusType()));
					orderDTO.setPaymentTypeParse(ParseUtils.convertPaymentType(order.getPaymentType()));
					orderDTO.setIsPaid(ParseUtils.convertByteArrayToBoolean(order.getIsPaid()));
					return orderDTO;
				});

	}

	@Override
	public Mono<OrderDTO> getOrderById(int id) {
		return orderRepository.findById(id)
				.switchIfEmpty(Mono.error(new RuntimeException("Order not found for id : " + id)))
				.flatMap(order -> convertToDto(order));

	}

	@Override
	public Flux<OrderDTO> getOrdersByUserId(int userId) {
		return orderRepository.findOrdersByUserId(userId).flatMap(this::convertToDto);
	}
}
