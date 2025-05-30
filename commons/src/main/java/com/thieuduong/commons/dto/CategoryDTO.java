package com.thieuduong.commons.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;

public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	@NotEmpty(message = "Name is required.")
	private String name;

	@NotEmpty(message = "Description is required.")
	private String description;

	private String imageUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
