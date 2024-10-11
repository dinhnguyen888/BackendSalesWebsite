package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.model.ProductDetail;
import com.example.backendsaleswebsite.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-details")
public class ProductDetailController {

    @Autowired
    private ProductDetailService productDetailService;

    // Tạo mới ProductDetail
    @PostMapping
    public ResponseEntity<ProductDetail> createProductDetail(@RequestBody ProductDetail productDetail) {
        ProductDetail createdProductDetail = productDetailService.createProductDetail(productDetail);
        return ResponseEntity.ok(createdProductDetail);
    }

    // Lấy tất cả ProductDetail
    @GetMapping
    public ResponseEntity<List<ProductDetail>> getAllProductDetails() {
        List<ProductDetail> productDetails = productDetailService.getAllProductDetails();
        return ResponseEntity.ok(productDetails);
    }

    // Lấy ProductDetail theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetail> getProductDetailById(@PathVariable Long id) {
        ProductDetail productDetail = productDetailService.getProductDetailById(id);
        return ResponseEntity.ok(productDetail);
    }

    // Cập nhật ProductDetail
    @PutMapping("/{id}")
    public ResponseEntity<ProductDetail> updateProductDetail(@PathVariable Long id, @RequestBody ProductDetail productDetailDetails) {
        ProductDetail updatedProductDetail = productDetailService.updateProductDetail(id, productDetailDetails);
        return ResponseEntity.ok(updatedProductDetail);
    }

    // Xóa ProductDetail
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductDetail(@PathVariable Long id) {
        productDetailService.deleteProductDetail(id);
        return ResponseEntity.noContent().build();
    }
}
