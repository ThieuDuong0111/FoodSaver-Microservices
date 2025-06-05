package com.thieuduong.order_service.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.commons.clients.IUserClient;
import com.thieuduong.commons.dto.CartItemOrderDTO;
import com.thieuduong.commons.dto.CompleteOrderDTO;
import com.thieuduong.commons.dto.OrderDTO;
import com.thieuduong.commons.dto.OrderDetailDTO;
import com.thieuduong.commons.utils.ParseUtils;
import com.thieuduong.order_service.models.Order;
import com.thieuduong.order_service.models.OrderDetail;
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

	@Override
	public Flux<OrderDTO> completeOrder(CompleteOrderDTO request) {
		List<CartItemOrderDTO> validatedItems = request.getValidatedItems();
		List<Integer> creatorIds = request.getCreatorIds();
		OrderDTO orderDTO = request.getOrderDTO();
		Integer userId = request.getUserId();

		return Flux.fromIterable(creatorIds).flatMap(creatorId -> {
			// Mỗi order là của riêng 1 creator
			Order order = null;
			try {
				order = new Order(userId, creatorId, userClient.getStoreById(creatorId).getStoreName(),
						LocalDateTime.now(), ParseUtils.generateOrderCode(), new byte[] { 0 }, 0,
						orderDTO.getPaymentType(), orderDTO.getShippingType());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			List<OrderDetail> orderDetails = new ArrayList<>();
			for (CartItemOrderDTO cartItem : validatedItems) {
				if (creatorId == cartItem.getCartProduct().getCreator().getId()) {

					orderDetails.add(new OrderDetail(order.getId(), cartItem.getCartProduct().getId(),
							cartItem.getCartProduct().getName(), cartItem.getCartProduct().getImage(),
							cartItem.getCartProduct().getImageUrl(), cartItem.getCartProduct().getImageType(),
							cartItem.getUnitQuantity(), cartItem.getUnitPrice()));
				}
			}

			order.setTotalAmount(calculateTotalAmountOfOrder(orderDetails));

			return orderRepository.save(order).flatMap(savedOrder -> {
				// Cập nhật orderId cho mỗi orderDetail
				orderDetails.forEach(od -> od.setOrderId(savedOrder.getId()));
				return orderDetailRepository.saveAll(orderDetails).then(convertToDto(savedOrder));

			});
		});
	}

	@Override
	public BigDecimal calculateTotalAmountOfOrder(List<OrderDetail> orderDetails) {
		double totalAmount = 0.0;
		if (orderDetails.size() == 0) {
			return new BigDecimal(totalAmount);
		}
		for (int i = 0; i < orderDetails.size(); i++) {
			totalAmount = totalAmount + orderDetails.get(i).getUnitPrice() * orderDetails.get(i).getUnitQuantity();

		}
		return new BigDecimal(totalAmount);
	}
}
