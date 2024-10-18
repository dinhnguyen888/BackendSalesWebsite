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
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deliveryId; // delivery_id

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "orderId", nullable = false)
    private Order order; // order_id, khóa ngoại

    @Column(nullable = false)
    private String deliveryState; // delivery_state

    @Column(nullable = false)
    private Date deliveryDate; // delivery_date
}
