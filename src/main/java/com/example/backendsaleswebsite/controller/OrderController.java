package com.example.backendsaleswebsite.controller;

import com.example.backendsaleswebsite.dto.DeliveryRequestDTO;
import com.example.backendsaleswebsite.dto.OrderDetailsDTO;
import com.example.backendsaleswebsite.repository.*;
import com.example.backendsaleswebsite.dto.OrderRequestDTO;
import com.example.backendsaleswebsite.dto.PaymentRequestDTO;
import com.example.backendsaleswebsite.service.AccountService;
import com.example.backendsaleswebsite.service.DeliveryService;
import com.example.backendsaleswebsite.service.OrderNotificationService;
import com.example.backendsaleswebsite.service.OrderService;
import com.example.backendsaleswebsite.service.PaymentService;
import com.example.backendsaleswebsite.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backendsaleswebsite.repository.AccountRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.example.backendsaleswebsite.model.*;
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private PaymentService paymentService; // Inject PaymentService

    @Autowired
    private DeliveryService deliveryService; // Inject DeliveryService

    @Autowired
    private AccountRepository accountRepository; // Inject AccountRepository

    @Autowired
    private AccountService accountService; 
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderNotificationService orderNotificationService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    
    @PostMapping
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody OrderRequestDTO orderRequestDTO, 
                                                           HttpServletRequest request) {
        // Tạo đơn hàng trong hệ thống
        OrderRequestDTO createdOrder = orderService.createOrder(orderRequestDTO);

        // Khởi tạo đối tượng trả về
        Map<String, String> response = new HashMap<>();
        Account account = accountRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
        
        if ("chuyển khoản".equalsIgnoreCase(orderRequestDTO.getPaymentMethod())) {
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String vnpayUrl = vnPayService.createOrder(createdOrder.getTotalCost().intValue(), 
                    createdOrder.getOrderId().toString(), baseUrl);
            
            response.put("redirectUrl", vnpayUrl);
        } else if ("tiền mặt".equalsIgnoreCase(orderRequestDTO.getPaymentMethod())) {
            PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
            paymentRequestDTO.setOrderId(createdOrder.getOrderId());
            paymentRequestDTO.setPaymentDate(Date.valueOf(LocalDate.now())); 
            paymentRequestDTO.setPaymentAmount(createdOrder.getTotalCost().doubleValue());
            paymentRequestDTO.setPaymentStatus("chưa trả"); 

            paymentService.createPayment(paymentRequestDTO);

            // Tạo Delivery
            DeliveryRequestDTO deliveryRequestDTO = new DeliveryRequestDTO();
            deliveryRequestDTO.setOrderId(createdOrder.getOrderId());
            deliveryRequestDTO.setDeliveryState("chưa giao");
            deliveryRequestDTO.setDeliveryDate(Date.valueOf(LocalDate.now())); 
            
            // Lưu Delivery vào database
            deliveryService.createDelivery(deliveryRequestDTO);
            
            // Trả về thông tin đơn hàng
            response.put("message", "tạo đơn hàng thành công với phương thức thanh toán là: " + orderRequestDTO.getPaymentMethod());
            Product product = productRepository.findById(orderRequestDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

            String productName = product.getProductName();
            Long productQuantity = createdOrder.getOrderQuantity();
            
            orderNotificationService.notifyOrder(productName,productQuantity.toString());
        } else {
            // Trả về thông tin đơn hàng nếu không phải phương thức thanh toán chuyển khoản hoặc tiền mặt
            response.put("message", "phương thức thanh toán sai.");
        }

        // Trả về phản hồi (ResponseEntity) với dữ liệu phản hồi và HTTP status OK (200)
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/cancel")
    public ResponseEntity<Map<String, String>> cancelOrder(@RequestParam Long orderId) {
        // Kiểm tra trạng thái của Payment và Delivery
        Optional<PaymentRequestDTO> payment = paymentService.getPaymentByOrderId(orderId);
        Optional<DeliveryRequestDTO> delivery = deliveryService.getDeliveryByOrderId(orderId);

        Map<String, String> response = new HashMap<>();

        if (payment.isPresent() && delivery.isPresent()) {
            if ("chưa trả".equalsIgnoreCase(payment.get().getPaymentStatus()) &&
                "chưa giao".equalsIgnoreCase(delivery.get().getDeliveryState())) {
                
                // Xóa đơn hàng nếu thỏa điều kiện
                orderService.deleteOrder(orderId);
                response.put("message", "Hủy đơn hàng thành công.");
            } else {
                response.put("message", "Không thể hủy đơn hàng vì đã thanh toán hoặc đang giao.");
            }
        } else {
            response.put("message", "Đơn hàng không tồn tại.");
        }

        // Trả về phản hồi (ResponseEntity) với dữ liệu phản hồi và HTTP status OK (200)
        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping
    public ResponseEntity<List<OrderDetailsDTO>> getAllOrders() {
        List<OrderDetailsDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsDTO> getOrderById(@PathVariable Long orderId) {
        OrderDetailsDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }


    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequestDTO orderRequestDTO) {
        Order updatedOrder = orderService.updateOrder(orderId, orderRequestDTO);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable Long orderId,
            @RequestParam String status) {
        
        String result = orderService.deleteOrderIfPending(orderId, status);
        
        if (result.contains("successfully deleted")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
}
