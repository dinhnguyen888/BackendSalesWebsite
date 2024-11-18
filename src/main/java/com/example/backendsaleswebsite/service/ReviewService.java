package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.ReviewDTO;
import com.example.backendsaleswebsite.model.Account;
import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.model.Review;
import com.example.backendsaleswebsite.repository.AccountRepository;
import com.example.backendsaleswebsite.repository.ProductRepository;
import com.example.backendsaleswebsite.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    // Tạo mới Review
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = mapToEntity(reviewDTO);
        review = reviewRepository.save(review);
        return mapToDTO(review);
    }

    // Cập nhật Review
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        // Fetch the existing review
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // Update fields
        existingReview.setReviewComment(reviewDTO.getReviewComment());
        existingReview.setReviewStar(reviewDTO.getReviewStar());

        // Update related entities if provided
        if (reviewDTO.getUserId() != null) {
            Account account = accountRepository.findById(reviewDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            existingReview.setAccount(account);
        }

        if (reviewDTO.getProductId() != null) {
            Product product = productRepository.findById(reviewDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            existingReview.setProduct(product);
        }

        // Save updated review
        Review updatedReview = reviewRepository.save(existingReview);
        return mapToDTO(updatedReview);
    }

    // Xóa Review
    public void deleteReview(Long id) {
        // Fetch the review to ensure it exists
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewRepository.delete(review);
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
        return mapToDTO(review);
    }

    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProduct_ProductId(productId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Phương thức chuyển đổi từ DTO sang Entity
    private Review mapToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReviewComment(reviewDTO.getReviewComment());
        review.setReviewStar(reviewDTO.getReviewStar());

        if (reviewDTO.getUserId() != null) {
            Account account = accountRepository.findById(reviewDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            review.setAccount(account);
        }

        if (reviewDTO.getProductId() != null) {
            Product product = productRepository.findById(reviewDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            review.setProduct(product);
        }

        return review;
    }

    // Phương thức chuyển đổi từ Entity sang DTO
    private ReviewDTO mapToDTO(Review review) {
        return new ReviewDTO(
                review.getReviewId(),
                review.getAccount().getUserId(),
                review.getProduct().getProductId(),
                review.getReviewComment(),
                review.getReviewStar()
        );
    }
}
