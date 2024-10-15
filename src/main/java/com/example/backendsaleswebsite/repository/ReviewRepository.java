package com.example.backendsaleswebsite.repository;

import com.example.backendsaleswebsite.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Các phương thức tùy chỉnh (nếu cần)
}
