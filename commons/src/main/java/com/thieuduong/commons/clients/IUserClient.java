package com.thieuduong.commons.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.thieuduong.commons.dto.UserDTO;

@FeignClient(name = "user-service", url = "http://localhost:8082", path = "/api")
public interface IUserClient {
	@GetMapping({ "/store/{id}" })
	UserDTO getStoreById(@PathVariable int id);

	@GetMapping({ "/store/get-10-newest-stores" })
	List<UserDTO> get10NewestStore();

}
