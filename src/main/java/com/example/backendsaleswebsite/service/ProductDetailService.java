package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.model.ProductDetail;
import com.example.backendsaleswebsite.repository.ProductDetailRepository;
import com.example.backendsaleswebsite.repository.ProductRepository;
import com.example.backendsaleswebsite.dto.ProductDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    // Tạo mới ProductDetail
    public ProductDetailDTO createProductDetail(ProductDetailDTO productDetailDTO) {
        // Lấy Product từ productId trong DTO
        Product product = productRepository.findById(productDetailDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Tạo ProductDetail từ Product và mô tả sản phẩm
        ProductDetail productDetail = ProductDetail.builder()
                .product(product)
                .productDescription(productDetailDTO.getProductDescription())
                .build();

        // Lưu ProductDetail vào cơ sở dữ liệu
        productDetail = productDetailRepository.save(productDetail);

        // Trả về DTO để phản hồi
        return mapToDTO(productDetail);
    }

    // Lấy tất cả ProductDetail
    public List<ProductDetailDTO> getAllProductDetails() {
        return productDetailRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Lấy ProductDetail theo ID
    public ProductDetailDTO getProductDetailById(Long id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Detail not found"));
        return mapToDTO(productDetail);
    }

    // Cập nhật ProductDetail
    public ProductDetailDTO updateProductDetail(Long id, ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Detail not found"));

        // Cập nhật thông tin
        productDetail.setProduct(productRepository.findById(productDetailDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found")));
        productDetail.setProductDescription(productDetailDTO.getProductDescription());

        // Lưu thay đổi
        productDetail = productDetailRepository.save(productDetail);
        return mapToDTO(productDetail);
    }

    // Xóa ProductDetail
    public void deleteProductDetail(Long id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Detail not found"));
        productDetailRepository.delete(productDetail);
    }

    // Phương thức chuyển đổi từ ProductDetail sang ProductDetailDTO
    private ProductDetailDTO mapToDTO(ProductDetail productDetail) {
        return new ProductDetailDTO(
                productDetail.getProductDetailId(),
                productDetail.getProduct().getProductId(),
                productDetail.getProductDescription()
        );
    }
}
