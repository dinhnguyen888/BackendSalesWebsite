package com.example.backendsaleswebsite.repository;

import com.example.backendsaleswebsite.model.Delivery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
    Optional<Delivery> findByOrderOrderId(Long orderId);
}
