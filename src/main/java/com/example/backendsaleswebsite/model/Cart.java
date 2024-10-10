package com.example.backendsaleswebsite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor  // Thêm constructor mặc định không tham số
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId", nullable = false)
    private Account account;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;
}
