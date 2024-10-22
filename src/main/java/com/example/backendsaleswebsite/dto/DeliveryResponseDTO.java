package com.example.backendsaleswebsite.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryResponseDTO {
	private Long orderId;
    private String deliveryState;
    private Date deliveryDate;
}
