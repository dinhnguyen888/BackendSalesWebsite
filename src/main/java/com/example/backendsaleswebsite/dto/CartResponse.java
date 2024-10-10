package com.example.backendsaleswebsite.dto;

import com.example.backendsaleswebsite.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartResponse {
    private Cart cart;
    private long totalCost;
}
