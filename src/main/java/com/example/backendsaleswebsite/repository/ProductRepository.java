package com.example.backendsaleswebsite.repository;

import com.example.backendsaleswebsite.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
