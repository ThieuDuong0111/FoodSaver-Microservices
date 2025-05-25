package com.thieuduong.feedback_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thieuduong.feedback_service.models.Feedback;

@Repository
public interface IFeedbackRepository extends JpaRepository<Feedback, Integer> {
	@Query(value = "SELECT * FROM feedback f WHERE f.product_id = :product_id ORDER BY f.id DESC", nativeQuery = true)
	List<Feedback> findByProductId(@Param("product_id") int productId);

	@Query(value = "SELECT AVG(f.rating) FROM feedback f WHERE f.product_id = :product_id", nativeQuery = true)
	Double findAverageRatingByProductId(@Param("product_id") int productId);

	@Query(value = "SELECT COUNT(f.comment) FROM feedback f WHERE f.product_id = :product_id AND f.comment IS NOT NULL", nativeQuery = true)
	int countCommentsByProductId(@Param("product_id") int productId);

	@Query(value = "SELECT COUNT(f.rating) FROM feedback f WHERE f.product_id = :product_id AND f.comment IS NOT NULL", nativeQuery = true)
	int countRatingsByProductId(@Param("product_id") int productId);

	@Query(value = "SELECT COUNT(f.rating) FROM feedback f WHERE f.product_id = :product_id AND f.rating = :rating AND f.comment IS NOT NULL", nativeQuery = true)
	int countRatingPointByProductId(@Param("product_id") int productId, @Param("rating") int ratingPoint);
}