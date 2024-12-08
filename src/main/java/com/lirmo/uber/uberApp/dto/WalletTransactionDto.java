package com.lirmo.uber.uberApp.dto;

import java.time.LocalDateTime;

import com.lirmo.uber.uberApp.enums.TransactionMethod;
import com.lirmo.uber.uberApp.enums.TransactionType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WalletTransactionDto {
    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private RideDto ride;

    private String transactionId;

    private LocalDateTime timeStamp;

    private WalletDto wallet;
}
