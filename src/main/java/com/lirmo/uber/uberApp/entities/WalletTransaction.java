package com.lirmo.uber.uberApp.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.lirmo.uber.uberApp.enums.TransactionMethod;
import com.lirmo.uber.uberApp.enums.TransactionType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionMethod transactionMethod;

    @OneToOne(fetch = FetchType.LAZY)
    private Ride ride;

    private String transactionId;
    @CreationTimestamp
    private LocalDateTime timeStamp;

    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;

}
