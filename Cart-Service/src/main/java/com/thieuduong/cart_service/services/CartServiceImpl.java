//package com.thieuduong.cart_service.services;
//
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.funix.foodsaverAPI.dto.CartByCreatorDTO;
//import com.funix.foodsaverAPI.dto.CartDTO;
//import com.funix.foodsaverAPI.dto.CartItemDTO;
//import com.funix.foodsaverAPI.dto.CartItemProductDTO;
//import com.funix.foodsaverAPI.dto.OrderDTO;
//import com.funix.foodsaverAPI.models.Cart;
//import com.funix.foodsaverAPI.models.CartItem;
//import com.funix.foodsaverAPI.models.MyUser;
//import com.funix.foodsaverAPI.models.Order;
//import com.funix.foodsaverAPI.models.OrderDetail;
//import com.funix.foodsaverAPI.models.Product;
//import com.funix.foodsaverAPI.repositories.ICartItemRepository;
//import com.funix.foodsaverAPI.repositories.ICartRepository;
//import com.funix.foodsaverAPI.repositories.IOrderDetailRepository;
//import com.funix.foodsaverAPI.repositories.IOrderRepository;
//import com.funix.foodsaverAPI.utils.ParseUtils;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@Service
//public class CartServiceImpl implements ICartService {
//
//	@Autowired
//	private ICartRepository cartRepository;
//
//	@Autowired
//	private ICartItemRepository cartItemRepository;
//
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
//
//	@Autowired
//	private ModelMapper modelMapper;
//
//	@Override
//	public CartDTO convertToDto(Cart cart) {
//		return modelMapper.map(cart, CartDTO.class);
//	}
//
//	@Override
//	public OrderDetail convertToOrderDetail(CartItem cartItem) {
//		return modelMapper.map(cartItem, OrderDetail.class);
//	}
//
//	@Override
//	public CartDTO getItems(HttpServletRequest request) {
//		MyUser user = userService.getUserByToken(request);
//		Optional<Cart> cart = cartRepository.getItemsByUserId(user.getId());
//		// If Cart not exist, create one
//		if (!cart.isPresent()) {
//			cartRepository
//				.save(new Cart(new ArrayList<CartItem>(), user, new Date(),
//					false));
//		}
//		Optional<Cart> cartGet = cartRepository.getItemsByUserId(user.getId());
//		CartDTO cartDTO = convertToDto(cartGet.get());
//		cartDTO.setTotalAmount(calculateTotalAmountOfCart(cartGet.get()));
//		cartDTO.setItemsCount(cartGet.get().getCartItems().size());
//		// --- Tách cartItems theo creator
//		seperateCartItemsByCreator(cartGet.get(), cartDTO);
//		return cartDTO;
//	}
//
//	@Transactional
//	@Override
//	public CartDTO updateItem(HttpServletRequest request,
//		CartItemProductDTO cartItemProductDTO) {
//		MyUser user = userService.getUserByToken(request);
//		Optional<Cart> cart = cartRepository.getItemsByUserId(user.getId());
//		if (!cart.isPresent()) {
//			cartRepository
//				.save(new Cart(new ArrayList<CartItem>(), user, new Date(),
//					false));
//			cart = cartRepository.getItemsByUserId(user.getId());
//		}
//		List<CartItem> cartItems = cart.get().getCartItems();
//		Boolean isItemExist = false;
//
//		// Check Item Exist
//		for (int i = 0; i < cartItems.size(); i++) {
//
//			// Check Product is exist in list cart items
//			if (cartItems.get(i).getCartProduct().getId() == cartItemProductDTO
//				.getId()) {
//				isItemExist = true;
//
//				int currentQuantity = cartItems.get(i).getUnitQuantity();
//
//				// Stop if quantity <= 0
//				if ((cartItems.get(i).getUnitQuantity()
//					+ cartItemProductDTO.getQuantity()) <= 0) {
//					break;
//				}
//				// Update Unit Quantity
//				cartItems.get(i).setUnitQuantity(
//					currentQuantity + cartItemProductDTO.getQuantity());
//				// Update Unit Price
//				cartItems.get(i).setUnitPrice(
//					productServiceImpl
//						.getProductById(cartItemProductDTO.getId()).getPrice());
//
//				// Save CartItem
//				cartItemRepository.save(cartItems.get(i));
//			}
//		}
//		// If Item Is Not Exist
//		if (!isItemExist) {
//			Product product = productServiceImpl
//				.getProductById(cartItemProductDTO.getId());
//			CartItem cartItem = new CartItem(cart.get(), product,
//				cartItemProductDTO.getQuantity(),
//				product.getPrice());
//
//			cart.get().getCartItems().add(cartItem);
//
//			// Save CartItem
//			cartItemRepository.save(cartItem);
//		}
//
//		// Return Cart
//		Optional<Cart> cartGet = cartRepository.getItemsByUserId(user.getId());
//		CartDTO cartDTO = convertToDto(cartGet.get());
//		cartDTO.setTotalAmount(calculateTotalAmountOfCart(cartGet.get()));
//		cartDTO.setItemsCount(cartGet.get().getCartItems().size());
//		// --- Tách cartItems theo creator
//		seperateCartItemsByCreator(cartGet.get(), cartDTO);
//		return cartDTO;
//
//	}
//
//	@Override
//	public CartDTO deleteItem(HttpServletRequest request,
//		CartItemDTO cartItemDTO) {
//		MyUser user = userService.getUserByToken(request);
//		Optional<Cart> cart = cartRepository.getItemsByUserId(user.getId());
//
//		// Delete Cart Item
//		cartItemRepository.deleteById(cartItemDTO.getId());
//
//		CartDTO cartDTO = convertToDto(cart.get());
//		cartDTO.setTotalAmount(calculateTotalAmountOfCart(cart.get()));
//		cartDTO.setItemsCount(cart.get().getCartItems().size());
//		// --- Tách cartItems theo creator
//		seperateCartItemsByCreator(cart.get(), cartDTO);
//
//		return cartDTO;
//	}
//
//	@Transactional
//	@Override
//	public CartDTO checkout(HttpServletRequest request)
//		throws IllegalArgumentException, ParseException {
//		MyUser user = userService.getUserByToken(request);
//		Cart currentCart = cartRepository.getItemsByUserId(user.getId()).get();
//		List<CartItem> cartItems = currentCart.getCartItems();
//
//		if (cartItems.size() > 0) {
//			// Check quantity
//			int currentProductQuantity = 0;
//			for (int i = 0; i < cartItems.size(); i++) {
//				currentProductQuantity = productServiceImpl
//					.getProductById(cartItems.get(i).getCartProduct().getId())
//					.getQuantity();
//				if (cartItems.get(i)
//					.getUnitQuantity() > currentProductQuantity) {
//					throw new IllegalArgumentException(
//						"Sản phẩm không đủ số lượng.");
//				}
//			}
//
////			// Update Product Quantity and Product SoldCount
////			for (int i = 0; i < cartItems.size(); i++) {
////				Product product = productServiceImpl
////					.getProductById(cartItems.get(i).getCartProduct().getId());
////				product.setQuantity(product.getQuantity() - cartItems.get(i)
////					.getUnitQuantity());
////				product.setSoldCount(product.getSoldCount()
////					+ cartItems.get(i).getUnitQuantity());
////			}
////
////			// Get Distinct Creator Id
////			List<Integer> creatorIds = cartRepository
////				.getDistinctCreatorId(currentCart.getId());
////
////			// Seperate Order
////			for (int i = 0; i < creatorIds.size(); i++) {
////
////				// Create Order
////				Order order = new Order(currentCart.getUserCarts(), new Date(),
////					ParseUtils.generateOrderCode(), false, creatorIds.get(i),
////					userService.getUserById(creatorIds.get(i)).getStoreName(),
////					0, 0);
////				List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
////				orderRepository.save(order);
////
////				for (int j = 0; j < cartItems.size(); j++) {
////					if (creatorIds.get(i) == cartItems.get(j).getCartProduct()
////						.getCreator().getId()) {
////						orderDetails.add(new OrderDetail(order,
////							cartItems.get(j).getCartProduct().getId(),
////							cartItems.get(j).getCartProduct().getName(),
////							cartItems.get(j).getCartProduct()
////								.getImageUrl(),
////							cartItems.get(j).getCartProduct().getImageType(),
////							cartItems.get(j).getCartProduct().getImage(),
////							cartItems.get(j).getUnitQuantity(),
////							cartItems.get(j).getUnitPrice()));
////					}
////				}
////				order.setOrderDetails(orderDetails);
////				order.setTotalAmount(calculateTotalAmountOfOrder(order));
////				orderDetailRepository.saveAll(order.getOrderDetails());
////			}
////
////			// Update Cart Done
////			currentCart.setIsDone(true);
////			cartRepository.save(currentCart);
////
////			// Create new cart
////			Cart newCart = new Cart(new ArrayList<CartItem>(), user, new Date(),
////				false);
////			cartRepository
////				.save(newCart);
//
//			CartDTO cartDTO = convertToDto(currentCart);
//			// --- Tách cartItems theo creator
//			cartDTO.setTotalAmount(calculateTotalAmountOfCart(currentCart));
//			cartDTO.setItemsCount(currentCart.getCartItems().size());
//			seperateCartItemsByCreator(currentCart, cartDTO);
//			return cartDTO;
//		} else {
//			CartDTO cartDTO = convertToDto(currentCart);
//			// --- Tách cartItems theo creator
//			cartDTO.setTotalAmount(calculateTotalAmountOfCart(currentCart));
//			cartDTO.setItemsCount(currentCart.getCartItems().size());
//			seperateCartItemsByCreator(currentCart, cartDTO);
//			return cartDTO;
//		}
//	}
//
//	@Override
//	public List<OrderDTO> completeOrder(OrderDTO orderDTO,
//		HttpServletRequest request)
//		throws ParseException {
//		List<OrderDTO> listOrders = new ArrayList<OrderDTO>();
//
//		MyUser user = userService.getUserByToken(request);
//		Cart currentCart = cartRepository.getItemsByUserId(user.getId()).get();
//		List<CartItem> cartItems = currentCart.getCartItems();
//
//		if (cartItems.size() > 0) {
//			// Check quantity
//			int currentProductQuantity = 0;
//			for (int i = 0; i < cartItems.size(); i++) {
//				currentProductQuantity = productServiceImpl
//					.getProductById(cartItems.get(i).getCartProduct().getId())
//					.getQuantity();
//				if (cartItems.get(i)
//					.getUnitQuantity() > currentProductQuantity) {
//					throw new IllegalArgumentException(
//						"Sản phẩm không đủ số lượng.");
//				}
//			}
//
//			// Update Product Quantity and Product SoldCount
//			for (int i = 0; i < cartItems.size(); i++) {
//				Product product = productServiceImpl
//					.getProductById(cartItems.get(i).getCartProduct().getId());
//				product.setQuantity(product.getQuantity() - cartItems.get(i)
//					.getUnitQuantity());
//				product.setSoldCount(product.getSoldCount()
//					+ cartItems.get(i).getUnitQuantity());
//			}
//
//			// Get Distinct Creator Id
//			List<Integer> creatorIds = cartRepository
//				.getDistinctCreatorId(currentCart.getId());
//
//			// Seperate Order
//			for (int i = 0; i < creatorIds.size(); i++) {
//
//				// Create Order
//				Order order = new Order(currentCart.getUserCarts(), new Date(),
//					ParseUtils.generateOrderCode(), false, creatorIds.get(i),
//					userService.getUserById(creatorIds.get(i)).getStoreName(),
//					0, 0);
//				// Set Payment Type
//				order.setPaymentType(orderDTO.getPaymentType());
//				// Set Shipping Type
//				order.setShippingType(orderDTO.getShippingType());
//				List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
//				orderRepository.save(order);
//
//				for (int j = 0; j < cartItems.size(); j++) {
//					if (creatorIds.get(i) == cartItems.get(j).getCartProduct()
//						.getCreator().getId()) {
//						orderDetails.add(new OrderDetail(order,
//							cartItems.get(j).getCartProduct().getId(),
//							cartItems.get(j).getCartProduct().getName(),
//							cartItems.get(j).getCartProduct()
//								.getImageUrl(),
//							cartItems.get(j).getCartProduct().getImageType(),
//							cartItems.get(j).getCartProduct().getImage(),
//							cartItems.get(j).getUnitQuantity(),
//							cartItems.get(j).getUnitPrice()));
//					}
//				}
//				order.setOrderDetails(orderDetails);
//				order.setTotalAmount(calculateTotalAmountOfOrder(order));
//				orderDetailRepository.saveAll(order.getOrderDetails());
//				listOrders.add(orderServiceImpl.convertToDto(order));
//			}
//
//			// Update Cart Done
//			currentCart.setIsDone(true);
//			cartRepository.save(currentCart);
//
//			// Create new cart
//			Cart newCart = new Cart(new ArrayList<CartItem>(), user, new Date(),
//				false);
//			cartRepository
//				.save(newCart);
//			return listOrders;
//		} else {
//			throw new IllegalArgumentException(
//				"Sản phẩm không đủ số lượng.");
//		}
//	}
//
//	@Override
//	public BigDecimal calculateTotalAmountOfCart(Cart cart) {
//		double totalAmount = 0.0;
//
//		if (cart.getCartItems().size() == 0) {
//			return new BigDecimal(totalAmount);
//		}
//		for (int i = 0; i < cart.getCartItems().size(); i++) {
//			totalAmount = totalAmount
//				+ cart.getCartItems().get(i).getUnitPrice()
//					* cart.getCartItems().get(i).getUnitQuantity();
//
//		}
//		return new BigDecimal(totalAmount);
//	}
//
//	@Override
//	public BigDecimal calculateTotalAmountOfOrder(Order order) {
//		double totalAmount = 0.0;
//		if (order.getOrderDetails().size() == 0) {
//			return new BigDecimal(totalAmount);
//		}
//		for (int i = 0; i < order.getOrderDetails().size(); i++) {
//			totalAmount = totalAmount
//				+ order.getOrderDetails().get(i).getUnitPrice()
//					* order.getOrderDetails().get(i).getUnitQuantity();
//
//		}
//		return new BigDecimal(totalAmount);
//	}
//
//	@Override
//	public void seperateCartItemsByCreator(Cart cart, CartDTO cartDTO) {
//		cartDTO.setCartByCreator(new ArrayList<CartByCreatorDTO>());
//		List<Integer> creatorIds = cartRepository
//			.getDistinctCreatorId(cart.getId());
//
//		for (int i = 0; i < creatorIds.size(); i++) {
//			List<CartItemDTO> cartItemDTO = new ArrayList<CartItemDTO>();
//			for (int j = 0; j < cart.getCartItems().size(); j++) {
//				// So sánh creatorId có trong cartItems
//				if (creatorIds.get(i) == cart.getCartItems().get(j)
//					.getCartProduct()
//					.getCreator().getId()) {
//					// Nếu có, add một đối tượng cartItemDTO vào list cartItems
//					cartItemDTO.add(
//						modelMapper.map(cart.getCartItems().get(j),
//							CartItemDTO.class));
//				}
//			}
//			cartDTO.getCartByCreator()
//				.add(new CartByCreatorDTO(cartItemDTO));
//		}
//	}
//}
