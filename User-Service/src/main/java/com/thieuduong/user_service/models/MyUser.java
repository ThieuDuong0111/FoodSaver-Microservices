package com.thieuduong.user_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "User", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }),
		@UniqueConstraint(columnNames = { "email" }), @UniqueConstraint(columnNames = { "phone" }), })
public class MyUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer roleId;

	@Column(length = 100, unique = true)
	private String name;

	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String avatar;

	@Column(columnDefinition = "TEXT")
	private String imageUrl;

	@Column(length = 20)
	private String imageType;

	@Column(length = 100, unique = true)
	private String storeName;

	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String storeImage;

	@Column(columnDefinition = "TEXT")
	private String storeImageUrl;

	@Column(length = 20)
	private String storeImageType;

	@Column(columnDefinition = "TEXT")
	private String storeDescription;

	private String password;

	@Column(length = 50, unique = true)
	private String email;

	@Column(length = 20, unique = true)
	private String phone;

	@Column(length = 100)
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