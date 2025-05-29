package com.thieuduong.user_service.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.thieuduong.user_service.models.MyUser;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IUserRepository extends R2dbcRepository<MyUser, Integer> {
//	@Query(value = "SELECT * FROM User u WHERE u.role_id = :role_id")
//	Flux<MyUser> findAllByRoleId(@Parameter("role_id") int role_id, Pageable pageable);

	@Query(value = "SELECT * FROM User u WHERE u.role_id = 2 ORDER BY u.id DESC LIMIT 10")
	Flux<MyUser> get10NewestStore();

	@Query(value = "SELECT * FROM User u WHERE u.role_id = 2 ORDER BY u.id")
	Flux<MyUser> getAllStores();

	Mono<MyUser> findByName(String name);

	Mono<MyUser> findByImageUrl(String imageUrl);

	Mono<MyUser> findByStoreImageUrl(String imageUrl);

	Boolean existsByName(String name);

	Boolean existsByEmail(String email);

	Boolean existsByPhone(String phone);
}
