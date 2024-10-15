package com.example.backendsaleswebsite.repository;

import com.example.backendsaleswebsite.model.Category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	  List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);
}