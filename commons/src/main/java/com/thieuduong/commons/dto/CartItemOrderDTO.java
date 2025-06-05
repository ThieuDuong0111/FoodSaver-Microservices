package com.thieuduong.commons.dto;

public class CartItemOrderDTO {
	private int id;

	private ProductOrderDTO cartProduct;

	private int unitQuantity;

	private double unitPrice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProductOrderDTO getCartProduct() {
		return cartProduct;
	}

	public void setCartProduct(ProductOrderDTO cartProduct) {
		this.cartProduct = cartProduct;
	}

	public int getUnitQuantity() {
		return unitQuantity;
	}

	public void setUnitQuantity(int unitQuantity) {
		this.unitQuantity = unitQuantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
}
