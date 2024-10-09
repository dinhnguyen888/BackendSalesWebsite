package com.example.backendsaleswebsite.model;

import jakarta.persistence.*;
@Entity

public class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	@Column(nullable = true)
	private String productName;
	
	@Column(nullable = true)
	private Long productQuantity;
	
	@Column(nullable = true)
	private String productManufacturer;
	
	@Column(nullable = true)
	private Long productCost;
	
	@Column(nullable = true)
	private Long productCategoryId;
	
	//Getters and Setters
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
	public Long getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}
	
	
	public String getManufacturer() {
		return productManufacturer;
	}
	public void setProductManufacturer(String productManufacturer) {
		this.productManufacturer = productManufacturer;
	}
	
	
	public Long getProductCost() {
		return productCost;
	}
	public void setProductCost(Long productCost) {
		this.productCost = productCost;
	}
	
	
	public Long getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
}
