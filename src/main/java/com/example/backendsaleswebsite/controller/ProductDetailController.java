package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.service.ProductDetailService;
import com.example.backendsaleswebsite.dto.ProductDetailDTO;
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
    public ResponseEntity<ProductDetailDTO> createProductDetail(@RequestBody ProductDetailDTO productDetailDTO) {
        ProductDetailDTO createdProductDetail = productDetailService.createProductDetail(productDetailDTO);
        return ResponseEntity.ok(createdProductDetail);
    }

    // Lấy tất cả ProductDetail
    @GetMapping
    public ResponseEntity<List<ProductDetailDTO>> getAllProductDetails() {
        List<ProductDetailDTO> productDetails = productDetailService.getAllProductDetails();
        return ResponseEntity.ok(productDetails);
    }

    // Lấy ProductDetail theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> getProductDetailById(@PathVariable Long id) {
        ProductDetailDTO productDetail = productDetailService.getProductDetailById(id);
        return ResponseEntity.ok(productDetail);
    }

    // Cập nhật ProductDetail
    @PutMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> updateProductDetail(@PathVariable Long id, @RequestBody ProductDetailDTO productDetailDTO) {
        ProductDetailDTO updatedProductDetail = productDetailService.updateProductDetail(id, productDetailDTO);
        return ResponseEntity.ok(updatedProductDetail);
    }

    // Xóa ProductDetail
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductDetail(@PathVariable Long id) {
        productDetailService.deleteProductDetail(id);
        return ResponseEntity.noContent().build();
    }
}
