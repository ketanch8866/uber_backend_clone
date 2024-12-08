package com.lirmo.uber.uberApp.services;

import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.entities.User;
import com.lirmo.uber.uberApp.entities.Wallet;
import com.lirmo.uber.uberApp.enums.TransactionMethod;

public interface WalletService {
    Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride,TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride,TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);
}
