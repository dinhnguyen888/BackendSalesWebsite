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
	private String manufacturer;
	
	@Column(nullable = true)
	private Long cost;
	
	@Column(nullable = true)
	private Long categoryId;
	
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
	
	public String getManufacturer() {
		return manufacturer;
	}
	public void setmanufacturer(String productManufacturer) {
		this.manufacturer = productManufacturer;
	}
	
	public Long getCost() {
		return cost;
	}
	
	public void setCost(Long cost) {
		this.cost = cost;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
}
