package com.example.backendsaleswebsite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsDTO {
    private Long orderId;
    private String productName;
    private String userName;
    private Long orderQuantity;
    private String deliveryAddress;
    private Long totalCost;
    private String orderState;
    private String paymentMethod;
    private String paymentStatus;
    private String deliveryStatus;
}
