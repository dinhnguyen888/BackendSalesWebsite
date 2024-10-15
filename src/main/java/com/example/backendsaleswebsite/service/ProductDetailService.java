package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.model.ProductDetail;
import com.example.backendsaleswebsite.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    // Tạo mới ProductDetail
    public ProductDetail createProductDetail(ProductDetail productDetail) {
        return productDetailRepository.save(productDetail);
    }

    // Lấy tất cả ProductDetail
    public List<ProductDetail> getAllProductDetails() {
        return productDetailRepository.findAll();
    }

    // Lấy ProductDetail theo ID
    public ProductDetail getProductDetailById(Long id) {
        return productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Detail not found"));
    }

    // Cập nhật ProductDetail
    public ProductDetail updateProductDetail(Long id, ProductDetail productDetailDetails) {
        ProductDetail productDetail = getProductDetailById(id);
        productDetail.setProduct(productDetailDetails.getProduct());
        productDetail.setProductDescription(productDetailDetails.getProductDescription());
        return productDetailRepository.save(productDetail);
    }

    // Xóa ProductDetail
    public void deleteProductDetail(Long id) {
        ProductDetail productDetail = getProductDetailById(id);
        productDetailRepository.delete(productDetail);
    }
}
