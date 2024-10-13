package com.lirmo.uber.uberApp.strategies.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.strategies.DriverMatchingStrategy;

@Service
public class DriverMatchingHighestRatedStrategy implements DriverMatchingStrategy{

    @Override
    public List<Driver> findMatchingDriver(RideRequestDto rideRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findMatchingDriver'");
    }
    
}
