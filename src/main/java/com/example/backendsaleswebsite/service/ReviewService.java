package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.model.Review;
import com.example.backendsaleswebsite.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // Tạo mới Review
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    // Lấy tất cả Review
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Lấy Review theo ID
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    // Cập nhật Review
    public Review updateReview(Long id, Review reviewDetails) {
        Review review = getReviewById(id);
        review.setReviewComment(reviewDetails.getReviewComment());
        review.setReviewStar(reviewDetails.getReviewStar());
        review.setAccount(reviewDetails.getAccount());
        review.setProduct(reviewDetails.getProduct());
        return reviewRepository.save(review);
    }

    // Xóa Review
    public void deleteReview(Long id) {
        Review review = getReviewById(id);
        reviewRepository.delete(review);
    }
}
