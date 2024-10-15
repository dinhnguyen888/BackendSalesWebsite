package com.example.backendsaleswebsite.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_state", nullable = false)
    private OrderState orderState;

    @Column(name = "voucher_id")
    private Long voucherId;

    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;
}

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "payment_amount", nullable = false)
    private BigDecimal paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;
}

@Entity
@Table(name = "delivery")
@Data
@NoArgsConstructor
class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_state", nullable = false)
    private DeliveryState deliveryState;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;
}

// Enums
enum PaymentStatus {
    CHUA_TRA("Chưa trả"),
    DA_TRA("Đã trả");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

enum PaymentMethod {
    TIEN_MAT("Tiền mặt"),
    CHUYEN_KHOAN("Chuyển khoản");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

enum OrderState {
    DA_DAT_HANG("Đã đặt hàng"),
    DA_HUY_DON("Đã hủy đơn");

    private final String description;

    OrderState(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}

enum DeliveryState {
    CHUA_GIAO("Chưa giao"),
    DANG_GIAO("Đang giao"),
    DA_GIAO("Đã giao");

    private final String description;

    DeliveryState(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
