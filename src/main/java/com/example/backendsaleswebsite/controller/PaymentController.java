package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController

public class PaymentController {
    
    @Autowired
    private VNPayService vnPayService;

    @GetMapping("/home") 
    public Map<String, String> home(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to VNPay Demo API");
        return response;
    }

    @PostMapping("/submitOrder")
    public Map<String, String> submitOrder(@RequestParam("amount") int orderTotal,
                                           @RequestParam("orderInfo") String orderInfo,
                                           HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        
        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", vnpayUrl);
        return response;
    }

    @GetMapping("/vnpay-payment")
    public Map<String, Object> getPaymentResult(HttpServletRequest request){
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", orderInfo);
        response.put("totalPrice", totalPrice);
        response.put("paymentTime", paymentTime);
        response.put("transactionId", transactionId);
        response.put("paymentStatus", paymentStatus == 1 ? "success" : "fail");
        
        return response;
    }
}