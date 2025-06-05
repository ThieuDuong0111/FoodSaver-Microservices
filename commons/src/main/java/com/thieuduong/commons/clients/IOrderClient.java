package com.thieuduong.commons.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import com.thieuduong.commons.dto.CompleteOrderDTO;
import com.thieuduong.commons.dto.OrderDTO;

@FeignClient(name = "order-service", url = "http://localhost:8086", path = "/api/order")
public interface IOrderClient {
	@PostMapping(value = "/complete-order", consumes = "application/json")
	List<OrderDTO> completeOrder(CompleteOrderDTO completeOrderDTO);
}
