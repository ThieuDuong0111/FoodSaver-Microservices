package com.thieuduong.order_service.services;

import com.thieuduong.order_service.models.OrderDetail;

public interface IOrderDetailService {
	OrderDetail getOrderDetailByImageUrl(String url);
	
	OrderDetail getOrderDetailById(int id);
}
