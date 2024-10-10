package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.model.Category;
import com.example.backendsaleswebsite.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    //lấy danh sách tên Category
    public List<String> getAllCategoryNames() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getCategoryName)  // Lấy ra tên của từng Category
                .collect(Collectors.toList());
    }


    
    // Lấy danh sách tất cả các category
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> searchCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName);
    }


    // sửa cập nhật category
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Xóa category theo ID
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
