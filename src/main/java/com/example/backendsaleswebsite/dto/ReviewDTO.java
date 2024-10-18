package com.example.backendsaleswebsite.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor 
public class ReviewDTO {
    private Long reviewId;
    private Long userId; // Thay vì toàn bộ Account
    private Long productId; // Thay vì toàn bộ Product
    private String reviewComment;
    private String reviewStar;
}
