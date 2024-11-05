package com.lirmo.uber.uberApp.strategies.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.entities.RideRequestEntity;
import com.lirmo.uber.uberApp.services.DistanceService;
import com.lirmo.uber.uberApp.strategies.RideFareCalculationStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RiderFareSurgePriceFareCalculationStratergy implements RideFareCalculationStrategy {
    private final DistanceService distanceService;
    // private final AppProperties appProperties;
    // Logger log =
    // LoggerFactory.getLogger(RiderFareDefaultFareCalculationStrategy.class);

    @Override
    public double calculateFare(RideRequestEntity rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());
        // log.info("calculateFare-->" + RIDE_FARE_MULTIPLIER);
        return distance * RIDE_FARE_MULTIPLIER * SURGE_FACTOR;
    }

}
