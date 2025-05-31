package com.thieuduong.order_service.services;

import com.thieuduong.order_service.models.OrderDetail;

import reactor.core.publisher.Mono;

public interface IOrderDetailService {

	Mono<OrderDetail> getOrderDetailByImageUrl(String url);

	Mono<OrderDetail> getOrderDetailById(int id);
}
