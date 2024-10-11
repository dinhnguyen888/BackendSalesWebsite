package com.example.backendsaleswebsite.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backendsaleswebsite.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
    @Query("SELECT p FROM Product p JOIN p.category c WHERE (:categoryName = 'all' OR c.categoryName = :categoryName)")
    Page<Product> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);
}
