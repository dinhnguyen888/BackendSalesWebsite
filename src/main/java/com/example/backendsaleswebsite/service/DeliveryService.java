package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.DeliveryRequestDTO;
import com.example.backendsaleswebsite.model.Delivery;
import com.example.backendsaleswebsite.model.Order;
import com.example.backendsaleswebsite.repository.DeliveryRepository;
import com.example.backendsaleswebsite.repository.OrderRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, OrderRepository orderRepository) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
    }

    public Optional<DeliveryRequestDTO> getDeliveryByOrderId(Long orderId) {
        // Find Delivery by orderId and map it to DeliveryRequestDTO
        return deliveryRepository.findByOrderOrderId(orderId)
                .map(delivery -> {
                    DeliveryRequestDTO dto = new DeliveryRequestDTO();
                    dto.setOrderId(delivery.getOrder().getOrderId());
                    dto.setDeliveryState(delivery.getDeliveryState());
                    dto.setDeliveryDate(delivery.getDeliveryDate());
                    return dto;
                });
    }

    public Delivery createDelivery(DeliveryRequestDTO deliveryRequestDTO) {
        Order order = orderRepository.findById(deliveryRequestDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Delivery delivery = Delivery.builder()
                .order(order)
                .deliveryState(deliveryRequestDTO.getDeliveryState())
                .deliveryDate(deliveryRequestDTO.getDeliveryDate())
                .build();

        return deliveryRepository.save(delivery);
    }
}
