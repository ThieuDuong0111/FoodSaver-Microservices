package com.thieuduong.product_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service", url = "http://localhost:8082/api")
public interface ICategoryClient {
	@GetMapping("/category/{id}")
	CategoryDTO getCategoryById(@PathVariable("id") int identity);
}
