package com.thieuduong.product_service.services;

import java.util.List;

import com.thieuduong.commons.dto.ProductDTO;
import com.thieuduong.commons.dto.ProductOrderDTO;
import com.thieuduong.product_service.models.Product;

public interface IProductService {

	ProductDTO convertToDto(Product product);

	ProductOrderDTO convertToProductOrderDTO(Product product);

	void updateProduct(Integer id, int quantity, int soldCount);

	List<ProductDTO> getAllProducts();

	ProductDTO getProductById(int id);

	ProductOrderDTO getProductOrderById(int id);

	void setCreatorToProduct(ProductDTO productDTO, int userId);

	List<ProductDTO> getTop20NewestProducts();

	List<ProductDTO> getTop5MostPurchaseProducts();

	Product getProductByImageUrl(String url);

	void deleteProductById(int id);

	List<ProductDTO> findByCategoryId(int categoryId);

	List<ProductDTO> findByCategoryIdWithNativeQuery(int categoryId);

	List<ProductDTO> findByStoreId(int storeId);

	List<ProductDTO> searchByName(String name);
}
