package com.lirmo.uber.uberApp.strategies.manager;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.lirmo.uber.uberApp.strategies.DriverMatchingStrategy;
import com.lirmo.uber.uberApp.strategies.RideFareCalculationStrategy;
import com.lirmo.uber.uberApp.strategies.impl.DriverMatchingHighestRatedStrategy;
import com.lirmo.uber.uberApp.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.lirmo.uber.uberApp.strategies.impl.RiderFareDefaultFareCalculationStrategy;
import com.lirmo.uber.uberApp.strategies.impl.RiderFareSurgePriceFareCalculationStratergy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StrategyManager {

    private final DriverMatchingHighestRatedStrategy driverMatchingHighestRatedStrategy;
    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStrategy;
    private final RiderFareDefaultFareCalculationStrategy riderFareDefaultFareCalculationStrategy;
    private final RiderFareSurgePriceFareCalculationStratergy riderFareSurgePriceFareCalculationStratergy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating) {
        if (riderRating > 4.5) {
            return driverMatchingHighestRatedStrategy;
        } else {
            return driverMatchingNearestDriverStrategy;
        }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy() {
        // 7PM to 11PM
        LocalTime currentTime = LocalTime.now();
        LocalTime surgeStartTime = LocalTime.of(19, 0, 0);
        LocalTime surgeEndTime = LocalTime.of(23, 0, 0);
        if (currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime)) {
            return riderFareSurgePriceFareCalculationStratergy;
        } else {
            return riderFareDefaultFareCalculationStrategy;
        }
    }
}
