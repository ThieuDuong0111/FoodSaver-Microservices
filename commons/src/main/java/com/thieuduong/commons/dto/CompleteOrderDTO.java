package com.thieuduong.commons.dto;

import java.util.List;

public class CompleteOrderDTO {

	private List<CartItemOrderDTO> validatedItems;
	private List<Integer> creatorIds;
	private OrderDTO orderDTO;
	private Integer userId;

	public CompleteOrderDTO() {
		super();
	}

	public List<CartItemOrderDTO> getValidatedItems() {
		return validatedItems;
	}

	public void setValidatedItems(List<CartItemOrderDTO> validatedItems) {
		this.validatedItems = validatedItems;
	}

	public List<Integer> getCreatorIds() {
		return creatorIds;
	}

	public void setCreatorIds(List<Integer> creatorIds) {
		this.creatorIds = creatorIds;
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CompleteOrderDTO [validatedItems=" + validatedItems + ", creatorIds=" + creatorIds + ", orderDTO="
				+ orderDTO + ", userId=" + userId + "]";
	}

}
