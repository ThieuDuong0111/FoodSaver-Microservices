package com.thieuduong.commons.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderDTO {
	private int id;

	private List<OrderDetailDTO> orderDetails;

	private String publishedDate;

	private String orderCode;

	private BigDecimal totalAmount;

	private Boolean isPaid;

	private String creatorName;

	private UserDTO creator;

	private int statusType;

	private String statusTypeParse;

	private int paymentType;

	private String paymentTypeParse;

	private int shippingType;

	public OrderDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<OrderDetailDTO> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public UserDTO getCreator() {
		return creator;
	}

	public void setCreator(UserDTO creator) {
		this.creator = creator;
	}

	public int getStatusType() {
		return statusType;
	}

	public void setStatusType(int statusType) {
		this.statusType = statusType;
	}

	public int getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public String getStatusTypeParse() {
		return statusTypeParse;
	}

	public void setStatusTypeParse(String statusTypeParse) {
		this.statusTypeParse = statusTypeParse;
	}

	public String getPaymentTypeParse() {
		return paymentTypeParse;
	}

	public void setPaymentTypeParse(String paymentTypeParse) {
		this.paymentTypeParse = paymentTypeParse;
	}

	public int getShippingType() {
		return shippingType;
	}

	public void setShippingType(int shippingType) {
		this.shippingType = shippingType;
	}

}
