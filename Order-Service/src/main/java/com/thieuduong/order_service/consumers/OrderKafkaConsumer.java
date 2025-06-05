package com.thieuduong.order_service.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.thieuduong.commons.dto.CompleteOrderDTO;
import com.thieuduong.order_service.services.OrderServiceImpl;

@Component
public class OrderKafkaConsumer {

	@Autowired
	private OrderServiceImpl orderServiceImpl;

	@KafkaListener(topics = "complete-order-topic", groupId = "order-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumeOrder(CompleteOrderDTO completeOrderDTO) {
		System.out.println("Received OrderDTO from cart-service: " + completeOrderDTO);

		orderServiceImpl.completeOrder(completeOrderDTO).subscribe(orderDTO -> {
			System.out.println("Order created: " + orderDTO);
		}, error -> {
			System.err.println("Error creating order: " + error.getMessage());
		});
	}

}
