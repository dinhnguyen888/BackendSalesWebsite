package com.example.backendsaleswebsite.service;

import com.example.backendsaleswebsite.dto.OrderDetailsDTO;
import com.example.backendsaleswebsite.dto.OrderRequestDTO;
import com.example.backendsaleswebsite.model.Order;
import com.example.backendsaleswebsite.model.Product;
import com.example.backendsaleswebsite.model.Account;
import com.example.backendsaleswebsite.model.Voucher;
import com.example.backendsaleswebsite.repository.OrderRepository;
import com.example.backendsaleswebsite.repository.PaymentRepository;
import com.example.backendsaleswebsite.repository.ProductRepository;
import com.example.backendsaleswebsite.repository.AccountRepository;
import com.example.backendsaleswebsite.repository.DeliveryRepository;
import com.example.backendsaleswebsite.repository.VoucherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final VoucherRepository voucherRepository;
    private final DeliveryService deliveryService; // Thêm vào
    private final PaymentService paymentService; // Thêm vào

    public OrderService(OrderRepository orderRepository, 
                        ProductRepository productRepository, 
                        AccountRepository accountRepository, 
                        VoucherRepository voucherRepository, 
                        DeliveryService deliveryService, // Thêm vào
                        PaymentService paymentService) { // Thêm vào
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.voucherRepository = voucherRepository;
        this.deliveryService = deliveryService; // Gán giá trị
        this.paymentService = paymentService; // Gán giá trị
    }
    
    @Autowired
    private DeliveryRepository deliveryRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
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

        // Kiểm tra và cập nhật số lượng sản phẩm
        if (product.getProductQuantity() < orderRequestDTO.getOrderQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough product quantity to place the order.");
        }

        product.setProductQuantity(product.getProductQuantity() - orderRequestDTO.getOrderQuantity());
        productRepository.save(product);

        Order order = Order.builder()
                .product(product)
                .account(account)
                .orderDate(LocalDateTime.now())
                .orderState(orderRequestDTO.getOrderState())
                .voucher(voucher)
                .orderQuantity(orderRequestDTO.getOrderQuantity())
                .deliveryAddress(orderRequestDTO.getDeliveryAddress())
                .totalCost(orderRequestDTO.getTotalCost())
                .paymentMethod(orderRequestDTO.getPaymentMethod())
                .build();

        Order savedOrder = orderRepository.save(order);

        return convertToDTO(savedOrder);
    }

    private OrderRequestDTO convertToDTO(Order order) {
        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setOrderId(order.getOrderId());
        dto.setProductId(order.getProduct().getProductId());
        dto.setUserId(order.getAccount().getUserId());
        dto.setVoucherId(order.getVoucher() != null ? order.getVoucher().getVoucherId() : null);
        dto.setOrderQuantity(order.getOrderQuantity());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setTotalCost(order.getTotalCost());
        dto.setOrderState(order.getOrderState());
        dto.setPaymentMethod(order.getPaymentMethod());
        return dto;
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Lấy sản phẩm từ đơn hàng và hoàn lại số lượng
        Product product = order.getProduct();
        product.setProductQuantity(product.getProductQuantity() + order.getOrderQuantity());
        productRepository.save(product);

        // Xóa đơn hàng
        orderRepository.deleteById(orderId);
    }
    
    @Transactional
    public OrderDetailsDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        
        return convertToOrderDetailsDTO(order);
    }

    @Transactional
    public List<OrderDetailsDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::convertToOrderDetailsDTO).collect(Collectors.toList());
    }


    @Transactional
    public Order updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        order.setOrderQuantity(orderRequestDTO.getOrderQuantity());
        order.setOrderState(orderRequestDTO.getOrderState());
        order.setDeliveryAddress(orderRequestDTO.getDeliveryAddress());
        order.setTotalCost(orderRequestDTO.getTotalCost());
        order.setPaymentMethod(orderRequestDTO.getPaymentMethod());

        return orderRepository.save(order);
    }
    
    private OrderDetailsDTO convertToOrderDetailsDTO(Order order) {
        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setOrderId(order.getOrderId());
        dto.setProductName(order.getProduct().getProductName());
        dto.setUserName(order.getAccount().getUserName());
        dto.setOrderQuantity(order.getOrderQuantity());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setTotalCost(order.getTotalCost());
        dto.setOrderState(order.getOrderState());
        dto.setPaymentMethod(order.getPaymentMethod());
        
        // Giả sử có các phương thức để lấy trạng thái thanh toán và giao hàng từ PaymentService và DeliveryService
        dto.setPaymentStatus(paymentService.getPaymentStatusByOrderId(order.getOrderId())
                .orElse("Đang chờ"));
dto.setDeliveryStatus(deliveryService.getDeliveryStateByOrderId(order.getOrderId())
                  .orElse("Đang chờ"));


        
        return dto;
    }
    
    @Transactional
    public String deleteOrderIfPending(Long orderId, String status) {
        if (!"Đang chờ".equals(status)) {
            return "Order cannot be deleted because the provided status is not 'Đang chờ'.";
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));

        orderRepository.delete(order);
        return "Order with id " + orderId + " has been successfully deleted.";
    }
    
    
}
