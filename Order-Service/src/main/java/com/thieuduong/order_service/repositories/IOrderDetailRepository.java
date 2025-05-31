package com.thieuduong.order_service.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thieuduong.order_service.models.OrderDetail;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IOrderDetailRepository extends R2dbcRepository<OrderDetail, Integer> {
	Mono<OrderDetail> findByProductImage(String imageUrl);

	@Query(value = "SELECT * FROM order_detail o WHERE o.order_id = :order_id")
	Flux<OrderDetail> findAllByOrderId(@Param("order_id")int orderId);
}
