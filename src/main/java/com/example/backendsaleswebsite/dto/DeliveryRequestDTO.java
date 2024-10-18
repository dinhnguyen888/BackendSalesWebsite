package com.example.backendsaleswebsite.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class DeliveryRequestDTO {
    private Long orderId;
    private String deliveryState;
    private Date deliveryDate;
}
