package com.thieuduong.cart_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thieuduong.cart_service.models.CartItem;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Integer> {
}
