package com.example.backendsaleswebsite.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = true)
    private String productName;

    @Column(nullable = true)
    private String manufacturer;
    
    @Column(nullable = true)
    private String productDescription;

    @Column(nullable = true)
    private Long cost;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;
}
