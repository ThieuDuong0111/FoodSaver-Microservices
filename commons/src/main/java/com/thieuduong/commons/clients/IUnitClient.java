package com.thieuduong.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.thieuduong.commons.dto.UnitDTO;

@FeignClient(name = "unit-service", url = "http://localhost:8083", path = "/api")
public interface IUnitClient {
	@GetMapping({ "/unit/{id}" })
	UnitDTO getUnitById(@PathVariable int id);
}
