package com.thieuduong.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.thieuduong.commons.dto.CategoryDTO;

@FeignClient(name = "category-service", url = "http://localhost:8082", path = "/api")
public interface ICategoryClient {
	@GetMapping({ "/category/{id}" })
	CategoryDTO getCategoryById(@PathVariable int id);
}
