package com.thieuduong.cart_service.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thieuduong.cart_service.models.CartItem;

import reactor.core.publisher.Flux;

@Repository
public interface ICartItemRepository extends R2dbcRepository<CartItem, Integer> {
	@Query(value = "SELECT * FROM cart_item ci WHERE ci.cart_id = :cart_id")
	Flux<CartItem> findAllByCartId(@Param("cart_id") int cartId);
}
