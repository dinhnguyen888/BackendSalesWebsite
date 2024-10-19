package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.dto.ReviewDTO;
import com.example.backendsaleswebsite.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Tạo mới Review
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        return ResponseEntity.ok(createdReview);
    }

    // Lấy tất cả Review
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    // Lấy Review theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        ReviewDTO review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    // Cập nhật Review
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    // Xóa Review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
