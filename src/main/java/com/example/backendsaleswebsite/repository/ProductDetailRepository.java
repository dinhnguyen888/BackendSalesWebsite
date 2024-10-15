package com.example.backendsaleswebsite.repository;

import com.example.backendsaleswebsite.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    // Bạn có thể thêm các phương thức tùy chỉnh tại đây nếu cần
}
