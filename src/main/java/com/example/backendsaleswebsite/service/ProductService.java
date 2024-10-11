package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Phương thức để lấy danh sách tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    //Phân trang cho product
    public Page<Product> getProductsByCategory(String categoryName, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findByCategoryName(categoryName, pageable);
    }
}
