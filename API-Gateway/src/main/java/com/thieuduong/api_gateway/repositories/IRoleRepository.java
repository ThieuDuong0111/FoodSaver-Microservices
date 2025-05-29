package com.thieuduong.api_gateway.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.thieuduong.api_gateway.models.Role;

import reactor.core.publisher.Mono;

@Repository
public interface IRoleRepository extends R2dbcRepository<Role, Integer> {
	Mono<Role> findById(int id);
}
