package com.thieuduong.product_service.repositories;

import com.thieuduong.product_service.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {
	Optional<Category> findByImageUrl(String imageUrl);
}