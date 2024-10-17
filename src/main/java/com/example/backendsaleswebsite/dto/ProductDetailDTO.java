package com.example.backendsaleswebsite.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDTO {
	private Long productDetailId;
	private Long ProductId;
	private String productDescription;
}
