package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.PaymentRequestDTO;
import com.example.backendsaleswebsite.model.Order;
import com.example.backendsaleswebsite.model.Payment;
import com.example.backendsaleswebsite.repository.OrderRepository;
import com.example.backendsaleswebsite.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    
    // Thêm phương thức deletePaymentByOrderId
    public void deletePaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng này"));

        paymentRepository.delete(payment);
    }

    // Thêm phương thức getAllPayments
    public List<PaymentRequestDTO> getAllPayments() {
        Iterable<Payment> payments = paymentRepository.findAll(); // CrudRepository trả về Iterable

        // Chuyển Iterable sang List
        List<Payment> paymentList = StreamSupport
                .stream(payments.spliterator(), false)
                .collect(Collectors.toList());

        return paymentList.stream()
                .map(payment -> {
                    PaymentRequestDTO dto = new PaymentRequestDTO();
                    dto.setOrderId(payment.getOrder().getOrderId());
                    dto.setPaymentDate(payment.getPaymentDate());
                    dto.setPaymentAmount(payment.getPaymentAmount());
                    dto.setPaymentStatus(payment.getPaymentStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    public Optional<String> getPaymentStatusByOrderId(Long orderId) {
        return paymentRepository.findByOrderOrderId(orderId)
                .map(Payment::getPaymentStatus);
    }

}
