package com.thieuduong.commons.dto;

import java.io.Serializable;

public class BannerDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;

	private String name;

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
