package com.example.backendsaleswebsite.repository;

import com.example.backendsaleswebsite.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Thêm phương thức tùy chỉnh để lấy Review theo productId
    List<Review> findByProduct_ProductId(Long productId);
}
