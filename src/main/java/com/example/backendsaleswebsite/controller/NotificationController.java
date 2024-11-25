package com.example.backendsaleswebsite.controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    // Lắng nghe thông báo từ client tại /app/order
    @MessageMapping("/order")
    @SendTo("/topic/notifications")
    public String sendNotification(String orderInfo) {
       
        return "New order received: " + orderInfo;
    }
}
