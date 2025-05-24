package com.thieuduong.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.thieuduong.commons.dto.UserDTO;

@FeignClient(name = "user-service", url = "http://localhost:8084", path = "/api")
public interface IUserClient {
	@GetMapping({ "/user/{id}" })
	UserDTO getUserById(@PathVariable int id);
}
