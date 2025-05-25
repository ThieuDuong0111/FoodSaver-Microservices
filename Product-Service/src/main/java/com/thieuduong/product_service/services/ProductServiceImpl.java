package com.thieuduong.product_service.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thieuduong.commons.clients.IFeedbackClient;
import com.thieuduong.commons.clients.IUserClient;
import com.thieuduong.commons.dto.FeedbackInformationDTO;
import com.thieuduong.commons.dto.ProductDTO;
import com.thieuduong.commons.dto.UserDTO;
import com.thieuduong.commons.utils.ParseUtils;
import com.thieuduong.product_service.models.Product;
import com.thieuduong.product_service.repositories.IProductRepository;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private IUserClient userClient;

	@Autowired
	private IFeedbackClient feedbackClient;

	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDTO convertToDto(Product product) {
		ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
		FeedbackInformationDTO feedbackInformationDTO = feedbackClient.getFeedbackInformation(product.getId());
		productDTO.setIsOutOfStock(product.getQuantity() < 1);
		productDTO.setIsExpired(ParseUtils.checkIsExpired(product.getExpiredDate()));
		// Rating count
		productDTO.setRatingsCount(feedbackInformationDTO.getRatingsCount());
		// Rating 1
		productDTO.setRating1Count(feedbackInformationDTO.getRating1Count());
		// Rating 2
		productDTO.setRating2Count(feedbackInformationDTO.getRating2Count());
		// Rating 3
		productDTO.setRating3Count(feedbackInformationDTO.getRating3Count());
		// Rating 4
		productDTO.setRating4Count(feedbackInformationDTO.getRating4Count());
		// Rating 5
		productDTO.setRating5Count(feedbackInformationDTO.getRating5Count());
		// Comment count
		productDTO.setCommentsCount(feedbackInformationDTO.getCommentsCount());
		productDTO.setRating(feedbackInformationDTO.getRating());
		setCreatorToProduct(productDTO, product.getCreatorId());
		return productDTO;
	}

//
//	@Override
//	public ProductDTO convertFromCartItemToProductDTO(CartItemDTO cartItemDTO) {
//		ProductDTO productDTO = modelMapper.map(cartItemDTO.getCartProduct(), ProductDTO.class);
//		return productDTO;
//	}
//
	@Override
	public List<ProductDTO> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(this::convertToDto).collect(Collectors.toList());
	}

//
	@Override
	public ProductDTO getProductById(int id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		Product product = null;
		if (optionalProduct.isPresent()) {
			product = optionalProduct.get();

			ProductDTO productDTO = convertToDto(product);
			setCreatorToProduct(productDTO, product.getCreatorId());
			return productDTO;
		} else {
			return null;
		}
	}

	@Override
	public List<ProductDTO> getTop20NewestProducts() {
		return productRepository.getTop20Products().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> getTop5MostPurchaseProducts() {
		return productRepository.getTop5MostPurchaseProducts().stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> findByCategoryId(int categoryId) {
		return productRepository.findByCategoryId(categoryId).stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}
//	
//	@Override
//	public List<ProductDTO> findByCategoryIdWithNativeQuery(int categoryId) {
//		return productRepository.findByCategoryIdWithNativeQuery(categoryId).stream().map(this::convertToDto)
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public List<ProductDTO> findByStoreId(int storeId) {
//		return productRepository.findByCreatorId(storeId).stream().map(this::convertToDto).collect(Collectors.toList());
//	}
//
//	@Override
//	public List<ProductDTO> searchByName(String name) {
//		System.out.println(name);
//		Specification<Product> specification = ProductSpecification.searchByKeyword(name);
//		return productRepository.findAll(specification).stream().map(this::convertToDto).collect(Collectors.toList());
//	}

	@Override
	public void deleteProductById(int id) {
		this.productRepository.deleteById(id);
	}

	@Override
	public Product getProductByImageUrl(String url) {
		Optional<Product> optionalProduct = productRepository.findByImageUrl(url);
		Product product = null;
		if (optionalProduct.isPresent()) {
			product = optionalProduct.get();
			return product;
		} else {
			return null;
		}
	}
//

//
//	@Override
//	public List<Product> testGetAll() {
//		return productRepository.testGetAll();
//	}

	@Override
	public void setCreatorToProduct(ProductDTO productDTO, int userId) {
		UserDTO userDTO = userClient.getUserById(userId);
		productDTO.setCreator(userDTO);
	}

}
