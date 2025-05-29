package com.thieuduong.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.thieuduong.commons.dto.ProductDTO;

@FeignClient(name = "product-service", url = "http://localhost:8081", path = "/api")
public interface IProductClient {
	@GetMapping("/product/{id}")
	ProductDTO getProductDetail(@PathVariable("id") int integer);
}
