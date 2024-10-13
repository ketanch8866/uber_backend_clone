package com.lirmo.uber.uberApp.strategies;

import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.entities.Driver;

import java.util.List;

public interface DriverMatchingStrategy {
     List<Driver> findMatchingDriver(RideRequestDto rideRequestDto);
}
