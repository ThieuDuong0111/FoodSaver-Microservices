package com.thieuduong.user_service.services;

import org.springframework.web.server.ServerWebExchange;

import com.thieuduong.commons.dto.UserDTO;
import com.thieuduong.user_service.models.MyUser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {
	UserDTO convertToDto(MyUser user);

	MyUser convertToEntity(UserDTO userDTO);

	Flux<MyUser> getAllUsers();

	Flux<UserDTO> getAllStores();

	Flux<UserDTO> get10NewestStore();

//	UserDTO updateUserInfo(HttpServletRequest request, UserDTO userDTO);
//
//	UserDTO updateUserInfoMobile(HttpServletRequest request, UserDTO userDTO);

	Mono<MyUser> getUserById(int id);

	Mono<MyUser> getUserByName(String name);

	Mono<MyUser> getUserByImageUrl(String url);

	Mono<MyUser> getUserByStoreImageUrl(String url);

	Mono<MyUser> getUserByToken(ServerWebExchange request) throws IllegalArgumentException;

}
