package com.thieuduong.cart_service.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

import com.thieuduong.commons.dto.CompleteOrderDTO;
import com.thieuduong.commons.dto.OrderDTO;

import java.util.List;

@Configuration
public class KafkaConsumerConfig {
	@Bean
	ReplyingKafkaTemplate<String, CompleteOrderDTO, List<OrderDTO>> replyingKafkaTemplate(
			ProducerFactory<String, CompleteOrderDTO> pf,
			ConcurrentMessageListenerContainer<String, List<OrderDTO>> container) {
		return new ReplyingKafkaTemplate<>(pf, container);
	}

	@Bean
	ConcurrentMessageListenerContainer<String, List<OrderDTO>> replyContainer(
			ConsumerFactory<String, List<OrderDTO>> cf) {
		ContainerProperties containerProperties = new ContainerProperties("order-response");
		return new ConcurrentMessageListenerContainer<>(cf, containerProperties);
	}

}
