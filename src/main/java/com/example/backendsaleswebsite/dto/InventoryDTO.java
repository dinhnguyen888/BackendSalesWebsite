package com.example.backendsaleswebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    private Long inventoryId;
    private Long productId; // Thay vì đối tượng Product, chỉ cần productId
    private Long productQuantity;
}
