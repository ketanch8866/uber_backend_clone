package com.lirmo.uber.uberApp.services.impl;

import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.entities.WalletTransaction;
import com.lirmo.uber.uberApp.repositories.WalletTransactionRepository;
import com.lirmo.uber.uberApp.services.WalletTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {
    private final WalletTransactionRepository walletTransactionRepository;

    @Override
    public void createNewWalletTrasaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }

}
