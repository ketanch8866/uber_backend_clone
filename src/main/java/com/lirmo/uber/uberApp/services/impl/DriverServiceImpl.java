package com.lirmo.uber.uberApp.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.RideDto;
import com.lirmo.uber.uberApp.dto.RiderDto;
import com.lirmo.uber.uberApp.services.DriverService;
@Service
public class DriverServiceImpl implements DriverService{

    @Override
    public RideDto acceptRide(Long rideId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'acceptRide'");
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelRide'");
    }

    @Override
    public RideDto startRide(Long rideId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startRide'");
    }

    @Override
    public RideDto endRide(Long rideId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endRide'");
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rateRider'");
    }

    @Override
    public DriverDto getMyProfile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMyProfile'");
    }

    @Override
    public List<RideDto> getAllMyRides() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMyRides'");
    }
    
}
