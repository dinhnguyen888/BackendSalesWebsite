package com.example.backendsaleswebsite.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Account account;

    @Column(name = "orderDate", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "orderState", nullable = false)  // Chuyển thành kiểu String
    private String orderState;

    @ManyToOne
    @JoinColumn(name = "voucherId", nullable = true)
    private Voucher voucher;
    
    @Column(name = "orderQuantity", nullable = false)
    private Long orderQuantity;
    
    @Column(name = "deliveryAddress", nullable = false)
    private String deliveryAddress;

    @Column(name = "totalCost", nullable = false)
    private Long totalCost;

    @Column(name = "paymentMethod", nullable = false)  // Chuyển thành kiểu String
    private String paymentMethod;
}
