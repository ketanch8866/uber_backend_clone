package com.lirmo.uber.uberApp.strategies;

import com.lirmo.uber.uberApp.dto.RideRequestDto;

public interface RideFareCalculationStrategy {
    double calculateFare(RideRequestDto rideRequestDto);
}
