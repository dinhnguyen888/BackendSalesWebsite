package com.example.backendsaleswebsite.dto;

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
public class VoucherResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long voucherId;

    @Column(nullable = false)
    private String voucherName;
    
    @Column(nullable = false)
    private long discount;
    
    @Column(nullable = false)
    private String userId;

   
}
