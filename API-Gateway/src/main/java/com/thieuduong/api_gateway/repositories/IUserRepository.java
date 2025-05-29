package com.thieuduong.api_gateway.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.thieuduong.api_gateway.models.MyUser;

import reactor.core.publisher.Mono;

@Repository
public interface IUserRepository extends R2dbcRepository<MyUser, Integer> {

	Mono<MyUser> findByName(String name);

	Mono<Boolean> existsByName(String name);

	Mono<Boolean> existsByEmail(String email);

	Mono<Boolean> existsByPhone(String phone);

}
