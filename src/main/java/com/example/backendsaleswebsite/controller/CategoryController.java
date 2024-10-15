package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.model.Category;
import com.example.backendsaleswebsite.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

 // Lấy danh sách tất cả tên category
    @GetMapping("/names")
    public ResponseEntity<List<String>> getAllCategoryNames() {
        List<String> categoryNames = categoryService.getAllCategoryNames();
        return ResponseEntity.ok(categoryNames);
    }

    
    // Lấy danh sách tất cả các category
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    //Tìm kiếm category
    @GetMapping("/search")
    public ResponseEntity<List<Category>> searchCategory(@RequestParam String name) {
        List<Category> categories = categoryService.searchCategoryByName(name);
        return ResponseEntity.ok(categories);
    }

    // Thêm mới category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category newCategory = categoryService.saveCategory(category);
        return ResponseEntity.ok(newCategory);
    }

    // Cập nhật category
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Category category = categoryService.getCategoryById(id);

        // Cập nhật thông tin category
        category.setCategoryName(categoryDetails.getCategoryName());

        Category updatedCategory = categoryService.saveCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }

    // Xóa category
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
