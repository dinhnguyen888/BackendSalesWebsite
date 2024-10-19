package com.example.backendsaleswebsite.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class PaymentRequestDTO {
    private Long orderId;
    private Date paymentDate;
    private double paymentAmount;
    private String paymentStatus;
}
