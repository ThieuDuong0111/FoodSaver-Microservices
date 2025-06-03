package com.thieuduong.commons.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {
	private int id;

	private List<CartByCreatorDTO> cartByCreator;

	private UserDTO userCarts;

	private String publishedDate;

	private Boolean isDone;

	private BigDecimal totalAmount;

	private int itemsCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public List<CartByCreatorDTO> getCartByCreator() {
		return cartByCreator;
	}

	public void setCartByCreator(List<CartByCreatorDTO> cartByCreator) {
		this.cartByCreator = cartByCreator;
	}

	public UserDTO getUserCarts() {
		return userCarts;
	}

	public void setUserCarts(UserDTO userCarts) {
		this.userCarts = userCarts;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}

}
