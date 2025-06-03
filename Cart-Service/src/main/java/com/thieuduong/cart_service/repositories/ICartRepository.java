package com.thieuduong.cart_service.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thieuduong.cart_service.models.Cart;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ICartRepository extends R2dbcRepository<Cart, Integer> {
	@Query(value = "SELECT * FROM Cart c WHERE c.user_id = :user_id AND c.is_done = false")
	Mono<Cart> getItemsByUserId(@Param("user_id") int userId);

//	@Query(value = "SELECT DISTINCT p.creator_id\r\n" + "FROM foodsaver.cart_item c\r\n"
//			+ "INNER JOIN foodsaver.product p\r\n" + "ON c.product_id=p.id\r\n" + "INNER JOIN foodsaver.user u\r\n"
//			+ "ON p.creator_id=u.id\r\n" + "WHERE c.cart_id = :cart_id\r\n"
//			+ "GROUP BY c.id, c.unit_price, c.unit_quantity, c.cart_id, c.product_id, p.creator_id")
//	List<Integer> getDistinctCreatorId(@Param("cart_id") int cartId);

	@Query(value = "SELECT DISTINCT ci.creator_id FROM cart_item ci WHERE ci.cart_id = :cart_id")
	Flux<Integer> getDistinctCreatorId(@Param("cart_id") int cartId);
}
