package com.lirmo.uber.uberApp.services;

import com.lirmo.uber.uberApp.entities.Payment;
import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.enums.PaymentStatus;

public interface PaymentService {
    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);
    void updatePaymentStatus(Payment payment,PaymentStatus status); 
}
