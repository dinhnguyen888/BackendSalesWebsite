package com.example.backendsaleswebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long productId;
    private String productName;
    private String manufacturer;
    private String productDescription;
    private Long cost;
    private String productImage;
    private Long productQuantity;
    private String categoryName; // Tên danh mục
    private Long categoryId; // ID danh mục
}
