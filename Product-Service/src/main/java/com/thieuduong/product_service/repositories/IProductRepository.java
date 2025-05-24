package com.thieuduong.product_service.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.thieuduong.product_service.models.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

//	@Query(value = "SELECT * FROM Product p ORDER BY p.id DESC LIMIT 20", nativeQuery = true)
//	List<Product> getTop20Products();
//
//	@Query(value = "SELECT * FROM Product p ORDER BY p.sold_count DESC LIMIT 5", nativeQuery = true)
//	List<Product> getTop5MostPurchaseProducts();
//
//	Optional<Product> findByImageUrl(String imageUrl);
//
	List<Product> findByCategoryId(int categoryId);
//
//	@Query(value = "SELECT * FROM Product p JOIN Category c ON p.category_id = c.id JOIN User u ON p.creator_id = u.id JOIN Unit un ON p.unit_id = un.id WHERE c.id = :category_id", nativeQuery = true)
//	List<Product> findByCategoryIdWithNativeQuery(@Param("category_id") int categoryId);
//
//	List<Product> findByCreatorId(int creatorId);
//
//	@Query(value = "SELECT * FROM Product", nativeQuery = true)
//	List<Product> testGetAll();
}
