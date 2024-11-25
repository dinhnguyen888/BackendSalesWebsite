package com.example.backendsaleswebsite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendsaleswebsite.dto.DeliveryResponseDTO;
import com.example.backendsaleswebsite.model.Delivery;
import com.example.backendsaleswebsite.service.DeliveryService;
import com.example.backendsaleswebsite.service.OrderNotificationService;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
	private final DeliveryService deliveryService;

	 @Autowired
	    private OrderNotificationService orderNotificationService;
	 
    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    // API để cập nhật trạng thái giao hàng
    @PutMapping("/{orderId}/state")
    public ResponseEntity<DeliveryResponseDTO> updateDeliveryState(
            @PathVariable Long orderId, 
            @RequestParam String newState) {
        
        Delivery updatedDelivery = deliveryService.updateDeliveryState(orderId, newState);
        
        // Chuyển đổi đối tượng Delivery sang DTO để trả về
        DeliveryResponseDTO responseDTO = new DeliveryResponseDTO();
        responseDTO.setOrderId(updatedDelivery.getOrder().getOrderId());
        responseDTO.setDeliveryState(updatedDelivery.getDeliveryState());
        responseDTO.setDeliveryDate(updatedDelivery.getDeliveryDate());
        orderNotificationService.notifystatus(orderId.toString(), newState);
        return ResponseEntity.ok(responseDTO);
    }
}
