package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.PaymentRequestDTO;
import com.example.backendsaleswebsite.model.Order;
import com.example.backendsaleswebsite.model.Payment;
import com.example.backendsaleswebsite.repository.OrderRepository;
import com.example.backendsaleswebsite.repository.PaymentRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public Optional<PaymentRequestDTO> getPaymentByOrderId(Long orderId) {
        // Find Payment by orderId and map it to PaymentRequestDTO
        return paymentRepository.findByOrderOrderId(orderId)
                .map(payment -> {
                    PaymentRequestDTO dto = new PaymentRequestDTO();
                    dto.setOrderId(payment.getOrder().getOrderId());
                    dto.setPaymentDate(payment.getPaymentDate());
                    dto.setPaymentAmount(payment.getPaymentAmount());
                    dto.setPaymentStatus(payment.getPaymentStatus());
                    return dto;
                });
    }

    
    public Payment createPayment(PaymentRequestDTO paymentRequestDTO) {
        Order order = orderRepository.findById(paymentRequestDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = Payment.builder()
                .order(order)
                .paymentDate(paymentRequestDTO.getPaymentDate())
                .paymentAmount(paymentRequestDTO.getPaymentAmount())
                .paymentStatus(paymentRequestDTO.getPaymentStatus())
                .build();

        return paymentRepository.save(payment);
    }
    
    public Payment updatePaymentStatus(Long orderId, String newStatus) {
        if (!isValidPaymentStatus(newStatus)) {
            throw new IllegalArgumentException("Trạng thái thanh toán không hợp lệ. Phải là 'đã trả' hoặc 'chưa trả'");
        }

        Payment payment = paymentRepository.findByOrderOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng này"));

        payment.setPaymentStatus(newStatus);

        return paymentRepository.save(payment);
    }

    private boolean isValidPaymentStatus(String status) {
        return "đã trả".equals(status) || "chưa trả".equals(status);
    }
}
