package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.dto.ProductDTO;
import com.example.backendsaleswebsite.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

 // Lấy danh sách tất cả sản phẩm
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Phân trang sản phẩm theo category
    @GetMapping("/pages")
    public ResponseEntity<Page<Product>> getProductsByCategory(
            @RequestParam String category,  // tên category hoặc "all"
            @RequestParam int limit,        // số sản phẩm trên mỗi trang
            @RequestParam int page          // trang hiện tại
    ) {
        if (page > 0) {
            page = page - 1; // Chuyển page từ 1 thành 0 (Spring Data JPA sử dụng index 0-based)
        }
        Page<Product> products = productService.getProductsByCategory(category, page, limit);
        return ResponseEntity.ok(products);
    }

    // Thêm sản phẩm mới
 // Thêm sản phẩm mới
    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO newProduct = productService.addProduct(productDTO);
        return ResponseEntity.ok(newProduct);
    }

    // Xóa sản phẩm theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

 // Cập nhật sản phẩm theo ID
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }
}