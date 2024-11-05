package com.lirmo.uber.uberApp.strategies;


import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.RideRequestEntity;

import java.util.List;

public interface DriverMatchingStrategy {
     List<Driver> findMatchingDrivers(RideRequestEntity rideRequest);
}
