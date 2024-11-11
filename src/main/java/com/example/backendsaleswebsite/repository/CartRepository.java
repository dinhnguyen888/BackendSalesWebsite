package com.example.backendsaleswebsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backendsaleswebsite.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByAccount_userId(Long accountId);
}
}