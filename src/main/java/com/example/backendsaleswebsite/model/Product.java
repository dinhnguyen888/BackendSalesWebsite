package com.example.backendsaleswebsite.model;

import com.example.backendsaleswebsite.model.Account.Role;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = true)
    private String productName;

    @Column(nullable = false)
    private String manufacturer;
    
    @Column(nullable = true)
    private String productDescription;

    @Column(nullable = false)
    private Long cost;
    
    @Column(nullable = true)
    private String productImage;
    
    @Column(nullable = false)
    private Long productQuantity;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;
}
