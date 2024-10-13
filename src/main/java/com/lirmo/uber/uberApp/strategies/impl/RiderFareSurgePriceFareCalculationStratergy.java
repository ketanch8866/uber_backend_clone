package com.lirmo.uber.uberApp.strategies.impl;

import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.strategies.RideFareCalculationStrategy;

@Service
public class RiderFareSurgePriceFareCalculationStratergy implements RideFareCalculationStrategy {

    @Override
    public double calculateFare(RideRequestDto rideRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateFare'");
    }

}
