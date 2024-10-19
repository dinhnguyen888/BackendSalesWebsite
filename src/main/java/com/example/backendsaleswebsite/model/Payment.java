package com.example.backendsaleswebsite.model;

import java.sql.Date;

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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId; // payment_id

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "orderId", nullable = false)
    private Order order; // order_id, khóa ngoại

    @Column(nullable = false)
    private Date paymentDate; // payment_date

    @Column(nullable = false)
    private double paymentAmount; // payment_amount

    @Column(nullable = false)
    private String paymentStatus; // payment_status
}
