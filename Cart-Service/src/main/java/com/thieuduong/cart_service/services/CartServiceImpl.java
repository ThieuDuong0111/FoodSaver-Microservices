package com.thieuduong.cart_service.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thieuduong.cart_service.models.Cart;
import com.thieuduong.cart_service.models.CartItem;
import com.thieuduong.cart_service.repositories.ICartItemRepository;
import com.thieuduong.cart_service.repositories.ICartRepository;
import com.thieuduong.commons.dto.CartDTO;
import com.thieuduong.commons.dto.CartItemDTO;
import com.thieuduong.commons.dto.CartItemOrderDTO;
import com.thieuduong.commons.dto.CartItemProductDTO;
import com.thieuduong.commons.dto.CompleteOrderDTO;
import com.thieuduong.commons.dto.OrderDTO;
import com.thieuduong.commons.dto.ProductDTO;
import com.thieuduong.commons.dto.ProductOrderDTO;
import com.thieuduong.commons.utils.ParseUtils;
//import com.thieuduong.commons.clients.IOrderClient;
import com.thieuduong.commons.clients.IProductClient;
import com.thieuduong.commons.clients.IUserClient;
import com.thieuduong.commons.dto.CartByCreatorDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	private ICartRepository cartRepository;

	@Autowired
	private ICartItemRepository cartItemRepository;

	@Autowired
	private IUserClient userClient;

	@Autowired
	private IProductClient productClient;

//	@Autowired
//	private IOrderClient orderClient;

	@Autowired
	private KafkaTemplate<String, CompleteOrderDTO> kafkaTemplate;

	@Override
	public Mono<CartDTO> convertToDto(Cart cart) {
		CartDTO cartDTO = new CartDTO();
		cartDTO.setId(cart.getId());
		cartDTO.setUserCarts(userClient.getStoreById(cart.getUserId()));
		cartDTO.setPublishedDate(ParseUtils.convertToDate(cart.getPublishedDate()));
		cartDTO.setIsDone(ParseUtils.convertByteArrayToBoolean(cart.getIsDone()));
		return Mono.just(cartDTO);
	}

	@Override
	public CartItemDTO convertoCartItemDTO(CartItem cartItem, ProductDTO productDTO) {
		CartItemDTO cartItemDTO = new CartItemDTO();
		cartItemDTO.setId(cartItem.getId());
		cartItemDTO.setCartProduct(productDTO);
		cartItemDTO.setUnitQuantity(cartItem.getUnitQuantity());
		cartItemDTO.setUnitPrice(cartItem.getUnitPrice());
		return cartItemDTO;
	}

	@Override
	public Mono<CartDTO> getItems(Integer userId) {
		return cartRepository.getItemsByUserId(userId) // Mono<Cart>
				.switchIfEmpty(
						// Nếu không có cart -> tạo cart mới
						cartRepository.save(new Cart(userId, LocalDateTime.now(), new byte[] { 0 })))
				.flatMap(cart ->
				// Gọi convertToDto(cart)
				convertToDto(cart).flatMap(cartDTO ->
				// Lấy cartItems từ repo
				cartItemRepository.findAllByCartId(cart.getId()).collectList().flatMap(cartItems ->
				// Gọi hàm xử lý
				{ // Gọi hàm xử lý tách theo creator
					return seperateCartItemsByCreator(cartItems, cartDTO).then(Mono.fromCallable(() -> {
						// Tính tổng số lượng sản phẩm
						cartDTO.setItemsCount(cartItems.size());
						// Tính tổng tiền từ danh sách item
						cartDTO.setTotalAmount(calculateTotalAmountOfCart(cartItems));
						return cartDTO;
					}));
				})));
	}

	@Override
	@Transactional
	public Mono<CartDTO> updateItem(Integer userId, CartItemProductDTO cartItemProductDTO) {
		return cartRepository.getItemsByUserId(userId)
				.switchIfEmpty(cartRepository.save(new Cart(userId, LocalDateTime.now(), new byte[] { 0 })))
				.flatMap(cart -> cartItemRepository.findAllByCartId(cart.getId()).collectList().flatMap(cartItems -> {
					Mono<Void> saveOperation = null;
					boolean isItemExist = false;

					// Check Item Exist
					for (int i = 0; i < cartItems.size(); i++) {

						// Check Product is exist in list cart items
						if (cartItems.get(i).getProductId().equals(cartItemProductDTO.getId())) {
							isItemExist = true;

							int currentQuantity = cartItems.get(i).getUnitQuantity();

							// Stop if quantity <= 0
							if ((cartItems.get(i).getUnitQuantity() + cartItemProductDTO.getQuantity()) <= 0) {
								break;
							}
							// Update Unit Quantity
							cartItems.get(i).setUnitQuantity(currentQuantity + cartItemProductDTO.getQuantity());
							// Update Unit Price
							ProductDTO productDTO = productClient.getProductDetail(cartItemProductDTO.getId());
							cartItems.get(i).setUnitPrice(productDTO.getPrice());
							// Update Creator Id
							cartItems.get(i).setCreatorId(productDTO.getCreator().getId());
							// Save CartItem
							saveOperation = cartItemRepository.save(cartItems.get(i)).then();
						}
					}

					// If Item Is Not Exist
					if (!isItemExist) {
						ProductDTO productDTO = productClient.getProductDetail(cartItemProductDTO.getId());
						CartItem newItem = new CartItem();
						newItem.setCartId(cart.getId());
						newItem.setProductId(productDTO.getId());
						newItem.setUnitQuantity(cartItemProductDTO.getQuantity());
						newItem.setUnitPrice(productDTO.getPrice());
						newItem.setCreatorId(productDTO.getCreator().getId());

						// Save CartItem
						saveOperation = cartItemRepository.save(newItem).then();
					}

					return saveOperation.then(cartItemRepository.findAllByCartId(cart.getId()).collectList())
							.flatMap(updatedItems -> convertToDto(cart).flatMap(cartDTO -> {
								cartDTO.setItemsCount(updatedItems.size());
								cartDTO.setTotalAmount(calculateTotalAmountOfCart(updatedItems));
								return seperateCartItemsByCreator(updatedItems, cartDTO).thenReturn(cartDTO);
							}));
				}));
	}

	@Override
	public Mono<CartDTO> deleteItem(Integer userId, CartItemDTO cartItemDTO) {
		return cartRepository.getItemsByUserId(userId)
				.flatMap(cart -> cartItemRepository.deleteById(cartItemDTO.getId()) // Xoá item
						.then(cartItemRepository.findAllByCartId(cart.getId()).collectList()) // Lấy lại danh sách item
																								// mới
						.flatMap(updatedItems -> {
							return convertToDto(cart).flatMap(cartDTO -> {
								cartDTO.setTotalAmount(calculateTotalAmountOfCart(updatedItems));
								cartDTO.setItemsCount(updatedItems.size());

								return seperateCartItemsByCreator(updatedItems, cartDTO).thenReturn(cartDTO);
							});
						}));
	}

	@Transactional
	@Override
	public Mono<CartDTO> checkout(Integer userId) throws IllegalArgumentException, ParseException {
		return cartRepository.getItemsByUserId(userId)
				.switchIfEmpty(Mono.error(new IllegalArgumentException("Không tìm thấy giỏ hàng")))
				.flatMap(cart -> cartItemRepository.findAllByCartId(cart.getId()).collectList().flatMap(cartItems -> {
					return Flux.fromIterable(cartItems).flatMap(cartItem -> {
						ProductDTO product = productClient.getProductDetail(cartItem.getProductId());
						if (cartItem.getUnitQuantity() > product.getQuantity()) {
							return Mono.error(
									new IllegalArgumentException("Sản phẩm không đủ số lượng: " + product.getName()));
						}
						return Mono.just(product);
					}).then(convertToDto(cart).flatMap(cartDTO -> {
						cartDTO.setItemsCount(cartItems.size());
						cartDTO.setTotalAmount(calculateTotalAmountOfCart(cartItems));
						return seperateCartItemsByCreator(cartItems, cartDTO).then(Mono.just(cartDTO));
					}));

				}));
	}

	@Transactional
	@Override
	public Flux<OrderDTO> completeOrder(Integer userId, OrderDTO orderDTO) {
		return cartRepository.getItemsByUserId(userId)
				.switchIfEmpty(Mono.error(new IllegalArgumentException("Không tìm thấy giỏ hàng")))
				.flatMapMany(cart -> cartItemRepository.findAllByCartId(cart.getId()).collectList()
						.flatMapMany(cartItems -> {
							if (cartItems.isEmpty()) {
								return Flux.error(new IllegalArgumentException("Giỏ hàng trống."));
							}

							// Kiểm tra số lượng tồn kho cho từng sản phẩm
							return Flux.fromIterable(cartItems)
									.flatMap(cartItem -> Mono.just(cartItem)
											.zipWith(Mono.fromCallable(
													() -> productClient.getProductDetail(cartItem.getProductId())))
											.flatMap(tuple -> {
												CartItem item = tuple.getT1();
												ProductDTO product = tuple.getT2();
												if (item.getUnitQuantity() > product.getQuantity()) {
													return Mono.error(new IllegalArgumentException(
															"Sản phẩm không đủ số lượng: " + product.getName()));
												}
												return Mono.just(item);
											}))
									.collectList().flatMapMany(validatedItems -> {
										// Cập nhật tồn kho và sold count
										for (CartItem item : validatedItems) {
											ProductDTO productDTO = productClient.getProductDetail(item.getProductId());
											productDTO.setQuantity(productDTO.getQuantity() - item.getUnitQuantity());
											productDTO.setSoldCount(productDTO.getSoldCount() + item.getUnitQuantity());
											productClient.updateProductAfterCompleteOrder(productDTO.getId(),
													productDTO.getQuantity(), productDTO.getSoldCount());
										}
										// Tách danh sách creatorId duy nhất từ validatedItems
										Set<Integer> creatorIds = validatedItems
												.stream().map(item -> productClient
														.getProductDetail(item.getProductId()).getCreator().getId())
												.collect(Collectors.toSet());

										// Tạo CompleteOrderDTO
										List<CartItemOrderDTO> validatedItemDTOs = validatedItems.stream().map(item -> {
											ProductOrderDTO product = productClient
													.getProductOrderDetail(item.getProductId());

											CartItemOrderDTO cartItemDTO = new CartItemOrderDTO();
											cartItemDTO.setCartProduct(product);
											cartItemDTO.setUnitQuantity(item.getUnitQuantity());
											cartItemDTO.setUnitPrice(item.getUnitPrice());
											return cartItemDTO;
										}).collect(Collectors.toList());

										// Gọi sang order-service
										CompleteOrderDTO completeOrderDTO = new CompleteOrderDTO();
										completeOrderDTO.setUserId(userId);
										completeOrderDTO.setValidatedItems(validatedItemDTOs);
										completeOrderDTO.setCreatorIds(new ArrayList<>(creatorIds));
										completeOrderDTO.setOrderDTO(orderDTO);
										// Gọi sang order-service qua kafka
										kafkaTemplate.send("complete-order-topic", completeOrderDTO);
										// Gọi sang order-service qua Feign client
//										List<OrderDTO> orderDTOList = orderClient.completeOrder(completeOrderDTO);
										List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();

										// Nếu chưa xử lý đơn hàng, chỉ cập nhật trạng thái cart và trả về rỗng
										cart.setIsDone(new byte[] { 1 });
										return cartRepository.save(cart)
												.then(cartRepository
														.save(new Cart(userId, LocalDateTime.now(), new byte[] { 0 })))
												.thenMany(Flux.fromIterable(orderDTOList));
									});
						}));
	}

	@Override
	public BigDecimal calculateTotalAmountOfCart(List<CartItem> cartItems) {
		double totalAmount = 0.0;

		if (cartItems.size() == 0) {
			return new BigDecimal(totalAmount);
		}
		for (int i = 0; i < cartItems.size(); i++) {
			totalAmount = totalAmount + cartItems.get(i).getUnitPrice() * cartItems.get(i).getUnitQuantity();

		}
		return new BigDecimal(totalAmount);
	}

	@Override
	public Mono<Void> seperateCartItemsByCreator(List<CartItem> cartItems, CartDTO cartDTO) {
		cartDTO.setCartByCreator(new ArrayList<CartByCreatorDTO>());

		return cartRepository.getDistinctCreatorId(cartDTO.getId()).collectList().map(creatorIds -> {

			for (Integer creatorId : creatorIds) {

				List<CartItemDTO> cartItemDTO = new ArrayList<CartItemDTO>();
				for (int j = 0; j < cartItems.size(); j++) {
					// So sánh creatorId có trong cartItems
					ProductDTO currentProduct = productClient.getProductDetail(cartItems.get(j).getProductId());
					if (creatorId.equals(currentProduct.getCreator().getId())) {
						// Nếu có, add một đối tượng cartItemDTO vào list cartItems
						cartItemDTO.add(convertoCartItemDTO(cartItems.get(j), currentProduct));
					}
				}
				cartDTO.getCartByCreator().add(new CartByCreatorDTO(cartItemDTO));
			}
			return cartDTO;
		}).then();
	}
}
