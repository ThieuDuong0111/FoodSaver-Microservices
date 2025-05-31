package com.thieuduong.order_service.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("OrderDetail")
public class OrderDetail {

	@Id
	private Integer id;

	@Column("order_id")
	private Integer orderId; // Foreign key được biểu diễn bằng kiểu đơn giản (Integer)

	@Column("product_id")
	private Integer productId;

	@Column("product_name")
	private String productName;

	// Không có @Lob trong R2DBC - dùng String hoặc ByteArray nếu cần blob thực sự
	private String image;

	@Column("product_image")
	private String productImage;

	@Column("image_type")
	private String imageType;

	@Column("unit_quantity")
	private int unitQuantity;

	@Column("unit_price")
	private double unitPrice;

	// Getters and Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
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
