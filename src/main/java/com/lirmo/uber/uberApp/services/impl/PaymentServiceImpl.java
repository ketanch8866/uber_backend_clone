package com.lirmo.uber.uberApp.services.impl;

import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.entities.Payment;
import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.enums.PaymentStatus;
import com.lirmo.uber.uberApp.exceptions.ResourceNotFoundException;
import com.lirmo.uber.uberApp.repositories.PaymentRepository;
import com.lirmo.uber.uberApp.services.PaymentService;
import com.lirmo.uber.uberApp.strategies.manager.PaymentStratergyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentStratergyManager paymentStratergyManager;

    @Override
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride).orElseThrow(
                () -> new ResourceNotFoundException("Payment not found for ride with id: " + ride.getId()));
        paymentStratergyManager.paymentStratergy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment = Payment.builder()
                .paymentMethod(ride.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .ride(ride)
                .amount(ride.getFare())
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }

}
