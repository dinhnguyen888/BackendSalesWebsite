package com.example.backendsaleswebsite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
	private Long orderId;
    private Long productId;
    private Long userId;
    private Long voucherId;
    private Long orderQuantity;
    private String deliveryAddress;
    private Long totalCost;
    private String orderState;      // String cho orderState
    private String paymentMethod;   // String cho paymentMethod
}
