package com.lirmo.uber.uberApp.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.lirmo.uber.uberApp.enums.TransactionMethod;
import com.lirmo.uber.uberApp.enums.TransactionType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "idx_wallet_transaction_wallet", columnList = "wallet_id"),
        @Index(name = "idx_wallet_transaction_ride", columnList = "ride_id"),
})
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    private TransactionMethod transactionMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ride ride;

    private String transactionId;
    @CreationTimestamp
    private LocalDateTime timeStamp;

    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet wallet;

}
