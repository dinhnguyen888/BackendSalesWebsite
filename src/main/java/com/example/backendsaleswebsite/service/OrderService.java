package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.OrderRequestDTO;
import com.example.backendsaleswebsite.model.Order;
import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.model.Account;
import com.example.backendsaleswebsite.model.Voucher;
import com.example.backendsaleswebsite.repository.OrderRepository;
import com.example.backendsaleswebsite.repository.ProductRepository;
import com.example.backendsaleswebsite.repository.AccountRepository;
import com.example.backendsaleswebsite.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final VoucherRepository voucherRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, AccountRepository accountRepository, VoucherRepository voucherRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.voucherRepository = voucherRepository;
    }

    public OrderRequestDTO createOrder(OrderRequestDTO orderRequestDTO) {
        Product product = productRepository.findById(orderRequestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Account account = accountRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Voucher voucher = null;
        if (orderRequestDTO.getVoucherId() != null) {
            voucher = voucherRepository.findById(orderRequestDTO.getVoucherId())
                    .orElseThrow(() -> new RuntimeException("Voucher not found"));
        }

        Order order = Order.builder()
                .product(product)
                .account(account)
                .orderDate(LocalDateTime.now())
                .orderState(orderRequestDTO.getOrderState())        
                .voucher(voucher)
                .totalCost(orderRequestDTO.getTotalCost())
                .paymentMethod(orderRequestDTO.getPaymentMethod())   // Truyền chuỗi trực tiếp
                .build();

        Order savedOrder = orderRepository.save(order);

        // Chuyển từ Order entity sang OrderRequestDTO để trả về
        return convertToDTO(savedOrder);
    }

    // Phương thức chuyển từ Order entity sang DTO
    private OrderRequestDTO convertToDTO(Order order) {
        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setOrderId(order.getOrderId()); // Thêm orderId từ đối tượng order
        dto.setProductId(order.getProduct().getProductId()); // lấy id từ đối tượng product
        dto.setUserId(order.getAccount().getUserId()); // lấy id từ đối tượng account
        dto.setVoucherId(order.getVoucher() != null ? order.getVoucher().getVoucherId() : null);
        dto.setTotalCost(order.getTotalCost());
        dto.setOrderState(order.getOrderState().toString()); // trả về enum dạng chuỗi
        dto.setPaymentMethod(order.getPaymentMethod().toString()); // trả về enum dạng chuỗi
        return dto;
    }
    
    public void deleteOrder(Long orderId) {
        // Xóa đơn hàng dựa trên orderId
        orderRepository.deleteById(orderId);
    }

}