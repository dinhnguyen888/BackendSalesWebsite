package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/pages")
    public ResponseEntity<Page<Product>> getProductsByCategory(
            @RequestParam String category,  // tên category hoặc "all"
            @RequestParam int limit,        // số sản phẩm trên mỗi trang
            @RequestParam int page          // trang hiện tại
    ) {
    	 if (page > 0) {
    	        page = page - 1; // Chuyển page từ 1 thành 0
    	    }
        Page<Product> products = productService.getProductsByCategory(category, page, limit);
        return ResponseEntity.ok(products);
    }
}
