package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.ReviewDTO;
import com.example.backendsaleswebsite.model.Review;
import com.example.backendsaleswebsite.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // Tạo mới Review
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = mapToEntity(reviewDTO);
        review = reviewRepository.save(review);
        return mapToDTO(review);
    }

    // Lấy tất cả Review
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Lấy Review theo ID
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return mapToDTO(review); // Chuyển đổi từ Review sang ReviewDTO
    }



    // Cập nhật Review
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review review = mapToEntity(getReviewById(id));
        review.setReviewComment(reviewDTO.getReviewComment());
        review.setReviewStar(reviewDTO.getReviewStar());
        // Cập nhật Account và Product nếu cần
        return mapToDTO(reviewRepository.save(review));
    }

    // Xóa Review
    public void deleteReview(Long id) {
        Review review = mapToEntity(getReviewById(id));
        reviewRepository.delete(review);
    }

    // Phương thức chuyển đổi từ DTO sang Entity
    private Review mapToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReviewId(reviewDTO.getReviewId());
        review.setReviewComment(reviewDTO.getReviewComment());
        review.setReviewStar(reviewDTO.getReviewStar());
        // Thiết lập Account và Product nếu có thông tin
        return review;
    }

    // Phương thức chuyển đổi từ Entity sang DTO
    private ReviewDTO mapToDTO(Review review) {
        return new ReviewDTO(
                review.getReviewId(),
                review.getAccount().getUserId(), // Lấy userId từ account
                review.getProduct().getProductId(), // Lấy productId từ product
                review.getReviewComment(),
                review.getReviewStar()
        );
    }
}
