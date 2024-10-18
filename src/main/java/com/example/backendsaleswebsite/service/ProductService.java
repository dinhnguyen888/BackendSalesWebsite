package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Phương thức để lấy danh sách tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Phân trang cho product
    public Page<Product> getProductsByCategory(String categoryName, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findByCategoryName(categoryName, pageable);
    }

    // Thêm sản phẩm mới
    public Product addProduct(Product product) {

        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            throw new RuntimeException("Product name is required");
        }

        if (product.getCost() == null || product.getCost() < 0) {
            throw new RuntimeException("Product cost must be a non-negative value");
        }

        if (product.getCategory() == null) {
            throw new RuntimeException("Product category is required");
        }

        return productRepository.save(product);
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
    }

    // Xóa sản phẩm theo ID
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    // Cập nhật thông tin sản phẩm
    public Product updateProduct(Long productId, Product product) {
        Product existingProduct = getProductById(productId); // Using the new getProductById method

        if (product.getProductName() != null && !product.getProductName().isEmpty()) {
            existingProduct.setProductName(product.getProductName());
        }

        if (product.getManufacturer() != null && !product.getManufacturer().isEmpty()) {
            existingProduct.setManufacturer(product.getManufacturer());
        }

        if (product.getCost() != null && product.getCost() >= 0) {
            existingProduct.setCost(product.getCost());
        }

        if (product.getCategory() != null) {
            existingProduct.setCategory(product.getCategory());
        }

        return productRepository.save(existingProduct);
    }
}
