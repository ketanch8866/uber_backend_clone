package com.lirmo.uber.uberApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lirmo.uber.uberApp.entities.Payment;
import com.lirmo.uber.uberApp.entities.Ride;
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByRide(Ride ride);
    
}
