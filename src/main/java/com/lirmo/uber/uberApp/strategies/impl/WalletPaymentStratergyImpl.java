package com.lirmo.uber.uberApp.strategies.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.Payment;
import com.lirmo.uber.uberApp.entities.Rider;
import com.lirmo.uber.uberApp.enums.PaymentStatus;
import com.lirmo.uber.uberApp.enums.TransactionMethod;
import com.lirmo.uber.uberApp.repositories.PaymentRepository;
import com.lirmo.uber.uberApp.services.WalletService;
import com.lirmo.uber.uberApp.strategies.PaymentStratergy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletPaymentStratergyImpl implements PaymentStratergy {
    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();
        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(), null, payment.getRide(),
                TransactionMethod.RIDE);

        double driverCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);
        walletService.addMoneyToWallet(driver.getUser(), driverCut, null, payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentTime(LocalDateTime.now());
        
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }

}
