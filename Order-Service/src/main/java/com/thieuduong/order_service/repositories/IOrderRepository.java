package com.thieuduong.order_service.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thieuduong.order_service.models.Order;

import reactor.core.publisher.Flux;

@Repository
public interface IOrderRepository extends R2dbcRepository<Order, Integer> {
	@Query(value = "SELECT * FROM my_order o WHERE o.user_id = :user_id ORDER BY o.id DESC")
	Flux<Order> findOrdersByUserId(@Param("user_id") int userId);

}
