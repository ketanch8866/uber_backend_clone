package com.lirmo.uber.uberApp.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.entities.User;
import com.lirmo.uber.uberApp.entities.Wallet;
import com.lirmo.uber.uberApp.entities.WalletTransaction;
import com.lirmo.uber.uberApp.enums.TransactionMethod;
import com.lirmo.uber.uberApp.enums.TransactionType;
import com.lirmo.uber.uberApp.exceptions.ResourceNotFoundException;
import com.lirmo.uber.uberApp.repositories.WalletRepository;
import com.lirmo.uber.uberApp.services.WalletService;
import com.lirmo.uber.uberApp.services.WalletTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;

    @Override
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride,
            TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);

        return handleMoneyIntoWallet(wallet, amount, transactionId, ride, transactionMethod);
    }

    @Override
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, Ride ride,
            TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        return handleMoneyIntoWallet(wallet, -amount, transactionId, ride, transactionMethod);
    }

    @Override
    public void withdrawAllMyMoneyFromWallet() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withdrawAllMyMoneyFromWallet'");
    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet is not found with id: " + walletId));

    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user with id: " + user.getId()));
    }

    @Transactional
    public Wallet handleMoneyIntoWallet(Wallet wallet, Double amount, String transactionId, Ride ride,
            TransactionMethod transactionMethod) {
        wallet.setBalance(wallet.getBalance() + amount);
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .amount(amount).transactionId(transactionId)
                .ride(ride)
                .transactionMethod(transactionMethod)
                .wallet(wallet)
                .transactionType((amount < 0) ? TransactionType.DEBIT : TransactionType.CREDIT)
                .build();

        // walletTransactionService.createNewWalletTrasaction(walletTransaction);
        wallet.getTransactions().add(walletTransaction);
        return walletRepository.save(wallet);
    }

}
