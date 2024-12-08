package com.lirmo.uber.uberApp.strategies.manager;

import org.springframework.stereotype.Component;

import com.lirmo.uber.uberApp.enums.PaymentMethod;
import com.lirmo.uber.uberApp.strategies.PaymentStratergy;
import com.lirmo.uber.uberApp.strategies.impl.CashPaymentStratergyImpl;
import com.lirmo.uber.uberApp.strategies.impl.WalletPaymentStratergyImpl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentStratergyManager {
    private final WalletPaymentStratergyImpl walletPaymentStratergy;
    private final CashPaymentStratergyImpl cashPaymentStratergy;

    public PaymentStratergy paymentStratergy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case CASH -> cashPaymentStratergy;
            case WALLET -> walletPaymentStratergy;
            // default -> throw new RuntimeConflictException("Invalid Payment method");
        };
    }
}
