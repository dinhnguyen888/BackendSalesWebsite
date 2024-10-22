package com.example.backendsaleswebsite.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDTO {
	private Long orderId;
    private Date paymentDate;
    private double paymentAmount;
    private String paymentStatus;
}
