package com.example.backendsaleswebsite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productDetailId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Lob // Để lưu chuỗi văn bản dài
    @Column(nullable = false)
    private String productDescription;
}
