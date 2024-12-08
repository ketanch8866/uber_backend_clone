package com.lirmo.uber.uberApp.strategies;

import com.lirmo.uber.uberApp.entities.Payment;

public interface PaymentStratergy {
    final double PLATFORM_COMMISSION = 0.3;

    void processPayment(Payment payment);
}
