package com.example.backendsaleswebsite.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor 
public class ReviewResponseDTO {
	private Long reviewId;
    private String userName;
    private Long userId; // Thay vì toàn bộ Account
    private Long productId; // Thay vì toàn bộ Product
    private String reviewComment;
    private Long reviewStar;
}
