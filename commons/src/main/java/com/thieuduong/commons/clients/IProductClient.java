package com.thieuduong.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.thieuduong.commons.dto.ProductDTO;
import com.thieuduong.commons.dto.ProductOrderDTO;

@FeignClient(name = "product-service", url = "http://localhost:8081", path = "/api", configuration = FeignResponseDecoderConfig.class)
public interface IProductClient {
	@GetMapping("/product/{id}")
	ProductDTO getProductDetail(@PathVariable("id") int integer);

	@GetMapping(value = "/product/update-product-after-complete-order")
	void updateProductAfterCompleteOrder(@RequestParam("product-id") Integer id,
			@RequestParam("product-quantity") int quantity, @RequestParam("product-soldcount") int soldCount);

	@GetMapping("/product-order/{id}")
	ProductOrderDTO getProductOrderDetail(@PathVariable("id") int integer);
}
