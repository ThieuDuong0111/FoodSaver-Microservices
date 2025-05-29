package com.thieuduong.api_gateway.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("User")
public class MyUser {

	@Id
	private Integer id;

	@Column("role_id")
	private Integer roleId; // Với R2DBC bạn nên dùng ID thay vì object Role trực tiếp

	private String name;

	private String avatar;

	@Column("image_url")
	private String imageUrl;

	@Column("image_type")
	private String imageType;

	@Column("store_name")
	private String storeName;

	@Column("store_image")
	private String storeImage;

	@Column("store_image_url")
	private String storeImageUrl;

	@Column("store_image_type")
	private String storeImageType;

	@Column("store_description")
	private String storeDescription;

	private String password;

	private String email;

	private String phone;

	private String address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreImage() {
		return storeImage;
	}

	public void setStoreImage(String storeImage) {
		this.storeImage = storeImage;
	}

	public String getStoreImageUrl() {
		return storeImageUrl;
	}

	public void setStoreImageUrl(String storeImageUrl) {
		this.storeImageUrl = storeImageUrl;
	}

	public String getStoreImageType() {
		return storeImageType;
	}

	public void setStoreImageType(String storeImageType) {
		this.storeImageType = storeImageType;
	}

	public String getStoreDescription() {
		return storeDescription;
	}

	public void setStoreDescription(String storeDescription) {
		this.storeDescription = storeDescription;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}