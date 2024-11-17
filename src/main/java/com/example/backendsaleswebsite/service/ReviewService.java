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

    private Review mapToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        // Không cần thiết lập reviewId, để JPA tự tăng
        review.setReviewComment(reviewDTO.getReviewComment());
        review.setReviewStar(reviewDTO.getReviewStar());

        // Thiết lập Account từ userId
        if (reviewDTO.getUserId() != null) {
            Account account = accountRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
            review.setAccount(account);
        }

        // Thiết lập Product từ productId
        if (reviewDTO.getProductId() != null) {
            Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
            review.setProduct(product);
        }

        return review;
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

    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProduct_ProductId(productId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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
//    private Review mapToEntity(ReviewDTO reviewDTO) {
//        Review review = new Review();
//        review.setReviewId(reviewDTO.getReviewId());
//        review.setReviewComment(reviewDTO.getReviewComment());
//        review.setReviewStar(reviewDTO.getReviewStar());
//        // Thiết lập Account và Product nếu có thông tin
//        return review;
//    }

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
