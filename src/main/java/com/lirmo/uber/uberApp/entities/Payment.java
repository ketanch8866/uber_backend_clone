package com.lirmo.uber.uberApp.entities;

import org.hibernate.annotations.CreationTimestamp;

import com.lirmo.uber.uberApp.enums.PaymentMethod;
import com.lirmo.uber.uberApp.enums.PaymentStatus;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    private Ride ride;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @CreationTimestamp
    private LocalDateTime paymentTime;
}
