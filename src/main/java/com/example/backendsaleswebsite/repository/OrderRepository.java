package com.example.backendsaleswebsite.repository;

import com.example.backendsaleswebsite.model.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByProductProductId(Long productId);
}
