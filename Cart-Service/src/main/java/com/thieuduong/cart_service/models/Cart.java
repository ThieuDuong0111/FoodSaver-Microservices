package com.thieuduong.cart_service.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Cart")
public class Cart {

	@Id
	private Integer id;

	@Column("user_id")
	private Integer userId;

	@Column("published_date")
	private LocalDateTime publishedDate;

	@Column("is_done")
	private byte[] isDone;

	// Constructors, Getters, Setters
	public Cart(Integer userId, LocalDateTime publishedDate, byte[] isDone) {
		super();
		this.userId = userId;
		this.publishedDate = publishedDate;
		this.isDone = isDone;
	}

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

	public LocalDateTime getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDateTime publishedDate) {
		this.publishedDate = publishedDate;
	}

	public byte[] getIsDone() {
		return isDone;
	}

	public void setIsDone(byte[] isDone) {
		this.isDone = isDone;
	}
}
