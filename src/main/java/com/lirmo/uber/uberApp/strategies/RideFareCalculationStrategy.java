package com.lirmo.uber.uberApp.strategies;

import com.lirmo.uber.uberApp.entities.RideRequestEntity;

public interface RideFareCalculationStrategy {
    double RIDE_FARE_MULTIPLIER = 10;
    double SURGE_FACTOR = 2;

    double calculateFare(RideRequestEntity rideRequestDto);
}
