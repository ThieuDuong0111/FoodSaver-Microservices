package com.thieuduong.order_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thieuduong.order_service.models.OrderDetail;


@Repository
public interface IOrderDetailRepository
	extends JpaRepository<OrderDetail, Integer> {
	Optional<OrderDetail> findByProductImage(String imageUrl);
}
