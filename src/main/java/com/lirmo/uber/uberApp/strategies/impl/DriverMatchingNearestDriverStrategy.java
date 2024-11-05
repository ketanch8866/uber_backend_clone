package com.lirmo.uber.uberApp.strategies.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.RideRequestEntity;
import com.lirmo.uber.uberApp.repositories.DriverRepository;
import com.lirmo.uber.uberApp.strategies.DriverMatchingStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {
    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDrivers(RideRequestEntity rideRequest) {

        return driverRepository.findTenNearestDrivers(rideRequest.getPickupLocation());
    }

}
