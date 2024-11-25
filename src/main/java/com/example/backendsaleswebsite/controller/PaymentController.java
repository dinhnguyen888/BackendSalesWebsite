package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backendsaleswebsite.dto.PaymentRequestDTO;
import com.example.backendsaleswebsite.dto.PaymentResponseDTO;
import com.example.backendsaleswebsite.model.Order;
import com.example.backendsaleswebsite.model.Payment;
import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.repository.OrderRepository;
import com.example.backendsaleswebsite.repository.ProductRepository;
import com.example.backendsaleswebsite.dto.DeliveryRequestDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaymentController {
    
    @Autowired
    private VNPayService vnPayService;
    private final PaymentService paymentService;
    private final DeliveryService deliveryService;

    @Autowired
    private OrderNotificationService orderNotificationService;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;
    
    public PaymentController(PaymentService paymentService, DeliveryService deliveryService) {
        this.paymentService = paymentService;
        this.deliveryService = deliveryService;
    }

//    @GetMapping("/home") 
//    public Map<String, String> home() {
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Welcome to VNPay Demo API");
//        return response;
//    }

    @PostMapping("/submitOrder")
    public Map<String, String> submitOrder(@RequestParam("amount") int orderTotal,
                                           @RequestParam("orderInfo") String orderInfo,
                                           HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        
        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", vnpayUrl);
        return response;
    }

    @GetMapping("/vnpay-payment")
    public Map<String, Object> getPaymentResult(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        // Chuyển đổi paymentTime sang LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDate paymentDate = LocalDate.parse(paymentTime, formatter);

        // Tạo đối tượng PaymentRequestDTO
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setOrderId(Long.parseLong(orderInfo));
        paymentRequestDTO.setPaymentDate(Date.valueOf(paymentDate)); // Chuyển LocalDate sang java.sql.Date
        paymentRequestDTO.setPaymentAmount(Double.parseDouble(totalPrice) / 100);
        paymentRequestDTO.setPaymentStatus("đã trả");

        // Tạo Payment qua service
        paymentService.createPayment(paymentRequestDTO);

        // Tạo đối tượng DeliveryRequestDTO
        DeliveryRequestDTO deliveryRequestDTO = new DeliveryRequestDTO();
        deliveryRequestDTO.setOrderId(Long.parseLong(orderInfo));
        deliveryRequestDTO.setDeliveryState("đang giao");
        deliveryRequestDTO.setDeliveryDate(Date.valueOf(paymentDate)); // Sử dụng cùng paymentDate đã chuyển đổi

        // Tạo Delivery qua service
        deliveryService.createDelivery(deliveryRequestDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("paymentStatus", paymentStatus == 1 ? "Thành công" : "Thất bại");
        response.put("transactionId", transactionId);
        

        Long orderId = Long.parseLong(orderInfo);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại"));
        Product product = order.getProduct();
        String productName = product.getProductName();
        Long productQuantity = order.getOrderQuantity();
        
        orderNotificationService.notifyOrder(productName,productQuantity.toString());
        return response;
    }
    
    @PutMapping("/update-status/{orderId}")
    public ResponseEntity<PaymentResponseDTO> updatePaymentStatus(
            @PathVariable Long orderId,
            @RequestParam String newStatus) {

        // Gọi service để cập nhật trạng thái thanh toán
        Payment updatedPayment = paymentService.updatePaymentStatus(orderId, newStatus);

        // Tạo DTO để trả về thông tin thanh toán đã được cập nhật
        PaymentResponseDTO responseDTO = new PaymentResponseDTO();
        responseDTO.setOrderId(updatedPayment.getOrder().getOrderId());
        responseDTO.setPaymentDate(updatedPayment.getPaymentDate());
        responseDTO.setPaymentAmount(updatedPayment.getPaymentAmount());
        responseDTO.setPaymentStatus(updatedPayment.getPaymentStatus());
        
        // Trả về HTTP status OK với thông tin thanh toán đã được cập nhật
        return ResponseEntity.ok(responseDTO);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PaymentRequestDTO>> getAllPayments() {
        List<PaymentRequestDTO> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    // Endpoint để xóa thanh toán theo Order ID
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<String> deletePaymentByOrderId(@PathVariable Long orderId) {
        paymentService.deletePaymentByOrderId(orderId);
        return ResponseEntity.ok("Thanh toán đã được xóa thành công");
    }
}
