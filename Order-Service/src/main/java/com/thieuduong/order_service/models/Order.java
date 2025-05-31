package com.thieuduong.order_service.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Table("my_order")
public class Order {

	@Id
	private Integer id;

	@Column("user_id")
	private Integer userId;

	@Column("creator_id")
	private Integer creatorId;

	@Column("creator_name")
	private String creatorName;

	@Column("published_date")
	private LocalDateTime publishedDate;

	@Column("order_code")
	private String orderCode;

	@Column("total_amount")
	private BigDecimal totalAmount;

	@Column("is_paid")
	private byte[] isPaid;

	@Column("status_type")
	private int statusType;

	@Column("payment_type")
	private int paymentType;

	@Column("shipping_type")
	private int shippingType;

	// Getters and Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public LocalDateTime getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDateTime publishedDate) {
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

	public byte[] getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(byte[] isPaid) {
		this.isPaid = isPaid;
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

	public int getShippingType() {
		return shippingType;
	}

	public void setShippingType(int shippingType) {
		this.shippingType = shippingType;
	}
}
