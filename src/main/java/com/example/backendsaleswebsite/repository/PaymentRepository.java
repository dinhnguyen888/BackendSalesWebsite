package com.example.backendsaleswebsite.repository;

import com.example.backendsaleswebsite.model.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {
    Optional<Payment> findByOrderOrderId(Long orderId);
}