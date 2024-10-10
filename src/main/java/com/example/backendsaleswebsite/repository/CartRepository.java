package com.example.backendsaleswebsite.repository;

import com.example.backendsaleswebsite.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByAccount_userId(Long accountId);
}