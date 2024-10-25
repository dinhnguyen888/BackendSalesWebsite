package com.example.backendsaleswebsite.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private String manufacturer;
    private String productDescription;
    private Long cost;
    private Long categoryId;
   
    private String productImage;
    private Long productQuantity;
}
