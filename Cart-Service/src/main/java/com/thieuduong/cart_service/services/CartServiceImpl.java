package com.thieuduong.cart_service.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.funix.foodsaverAPI.models.MyUser;
import com.funix.foodsaverAPI.models.Order;
import com.funix.foodsaverAPI.models.OrderDetail;
import com.funix.foodsaverAPI.models.Product;
import com.thieuduong.cart_service.models.Cart;
import com.thieuduong.cart_service.models.CartItem;
import com.thieuduong.cart_service.repositories.ICartItemRepository;
import com.thieuduong.cart_service.repositories.ICartRepository;
import com.thieuduong.commons.dto.CartDTO;
import com.thieuduong.commons.dto.CartItemDTO;
import com.thieuduong.commons.dto.CartItemProductDTO;
import com.thieuduong.commons.dto.OrderDTO;
import com.thieuduong.commons.dto.ProductDTO;
import com.thieuduong.commons.utils.ParseUtils;
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
//	private IOrderRepository orderRepository;
//
//	@Autowired
//	private IOrderDetailRepository orderDetailRepository;
//
//	@Autowired
//	private ProductServiceImpl productServiceImpl;
//
//	@Autowired
//	private OrderServiceImpl orderServiceImpl;
//
//	@Autowired
//	private IUserService userService;

//	@Autowired
//	private ModelMapper modelMapper;

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

//	@Override
//	public OrderDetail convertToOrderDetail(CartItem cartItem) {
//		return modelMapper.map(cartItem, OrderDetail.class);
//	}
//
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

	@Override
	public Flux<OrderDTO> completeOrder(Integer userId, OrderDTO orderDTO) throws ParseException {
		return cartRepository.getItemsByUserId(userId).flatMapMany(
				cart -> cartItemRepository.findAllByCartId(cart.getId()).collectList().flatMapMany(cartItems -> {
					if (cartItems.isEmpty()) {
						return Flux.error(new IllegalArgumentException("Giỏ hàng trống."));
					}

					// Kiểm tra số lượng tồn kho cho từng sản phẩm
					return Flux.fromIterable(cartItems).flatMap(cartItem -> Mono.just(cartItem)
							.zipWith(Mono.fromCallable(() -> productClient.getProductDetail(cartItem.getProductId())))
							.flatMap(tuple -> {
								CartItem item = tuple.getT1();
								ProductDTO product = tuple.getT2();
								if (item.getUnitQuantity() > product.getQuantity()) {
									return Mono.error(new IllegalArgumentException(
											"Sản phẩm không đủ số lượng: " + product.getName()));
								}
								return Mono.just(item);
							})).collectList().flatMapMany(validatedItems -> {
								// Cập nhật tồn kho và sold count
								for (CartItem item : validatedItems) {
									ProductDTO product = productClient.getProductDetail(item.getProductId());
									product.setQuantity(product.getQuantity() - item.getUnitQuantity());
									product.setSoldCount(product.getSoldCount() + item.getUnitQuantity());
//									productClient.updateProduct(product); // Giả định có method cập nhật product
								}

								// Lấy danh sách creatorId duy nhất
//								List<Integer> creatorIds = cartRepository.getDistinctCreatorId(cart.getId());

								// Tách đơn theo creator (comment toàn bộ phần tạo Order và OrderDetail)
								/*
								 * return Flux.fromIterable(creatorIds) .flatMap(creatorId -> { Order order =
								 * new Order( cart.getUserCarts(), new Date(), ParseUtils.generateOrderCode(),
								 * false, creatorId, userService.getUserById(creatorId).getStoreName(), 0, 0 );
								 * 
								 * order.setPaymentType(orderDTO.getPaymentType());
								 * order.setShippingType(orderDTO.getShippingType());
								 * 
								 * List<OrderDetail> orderDetails = new ArrayList<>(); for (CartItem item :
								 * validatedItems) { ProductDTO product =
								 * productClient.getProductDetail(item.getProductId()); if
								 * (product.getCreator().getId().equals(creatorId)) { orderDetails.add(new
								 * OrderDetail(order, product.getId(), product.getName(), product.getImageUrl(),
								 * product.getImageType(), product.getImage(), item.getUnitQuantity(),
								 * item.getUnitPrice())); } }
								 * 
								 * order.setOrderDetails(orderDetails);
								 * order.setTotalAmount(calculateTotalAmountOfOrder(order));
								 * 
								 * return orderRepository.save(order) .flatMap(savedOrder -> { // return
								 * orderDetailRepository.saveAll(orderDetails).then( //
								 * Mono.just(orderServiceImpl.convertToDto(savedOrder)) // ); return
								 * Mono.just(new OrderDTO(savedOrder.getId(), savedOrder.getOrderCode())); });
								 * }) .collectList() .flatMapMany(orderDTOList -> { cart.setIsDone(true); return
								 * cartRepository.save(cart) .then(cartRepository.save(new
								 * Cart(cart.getUserCarts(), new Date(), new byte[]{0})))
								 * .thenMany(Flux.fromIterable(orderDTOList)); });
								 */

								// Nếu chưa xử lý đơn hàng, chỉ cập nhật trạng thái cart và trả về rỗng
								cart.setIsDone(new byte[] { 1 });
								return cartRepository.save(cart)
										.then(cartRepository
												.save(new Cart(userId, LocalDateTime.now(), new byte[] { 0 })))
										.thenMany(Flux.empty());
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
