package com.example.backendsaleswebsite.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public OrderNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyOrder(String orderInfo, String productQuantity) {
        messagingTemplate.convertAndSend("/topic/notifications", "Bạn đã đặt hàng với sản phẩm: " + orderInfo +", số lượng là: " + productQuantity);
    }
    
    public void notifystatus(String orderInfo, String status) {
        messagingTemplate.convertAndSend("/topic/notifications", "Đơn hàng " + orderInfo +"đang ở trạng thái: " + status);
    }
}
