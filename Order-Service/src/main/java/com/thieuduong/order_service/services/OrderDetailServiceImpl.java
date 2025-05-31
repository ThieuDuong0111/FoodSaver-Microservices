package com.thieuduong.order_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.order_service.models.OrderDetail;
import com.thieuduong.order_service.repositories.IOrderDetailRepository;

import reactor.core.publisher.Mono;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

	@Autowired
	private IOrderDetailRepository orderDetailRepository;

	@Override
	public Mono<OrderDetail> getOrderDetailByImageUrl(String url) {
		return orderDetailRepository.findByProductImage(url);
	}

	@Override
	public Mono<OrderDetail> getOrderDetailById(int id) {
		return orderDetailRepository.findById(id);
	}
}
