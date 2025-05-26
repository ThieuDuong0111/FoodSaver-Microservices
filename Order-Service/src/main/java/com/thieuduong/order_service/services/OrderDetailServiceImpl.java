package com.thieuduong.order_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.order_service.models.OrderDetail;
import com.thieuduong.order_service.repositories.IOrderDetailRepository;

@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

	@Autowired
	private IOrderDetailRepository orderDetailRepository;

	@Override
	public OrderDetail getOrderDetailByImageUrl(String url) {
		Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findByProductImage(url);
		OrderDetail orderDetail = null;
		if (optionalOrderDetail.isPresent()) {
			orderDetail = optionalOrderDetail.get();
			return orderDetail;
		} else {
			return null;
		}
	}

	@Override
	public OrderDetail getOrderDetailById(int id) {
		Optional<OrderDetail> optionalOrderDetail = orderDetailRepository.findById(id);
		OrderDetail orderDetail = null;
		if (optionalOrderDetail.isPresent()) {
			orderDetail = optionalOrderDetail.get();
			return orderDetail;
		} else {
			return null;
		}
	}
}
