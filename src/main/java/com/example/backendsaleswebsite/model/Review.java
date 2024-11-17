package com.example.backendsaleswebsite.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@AllArgsConstructor
@NoArgsConstructor 
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private String reviewComment;
    
    @Column(nullable = false)
    private Long reviewStar;
    


   
}
