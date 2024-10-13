package com.lirmo.uber.uberApp.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.RideDto;
import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.dto.RiderDto;
import com.lirmo.uber.uberApp.services.RiderService;
@Service
public class RiderServiceImpl implements RiderService{

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'requestRide'");
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelRide'");
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rateDriver'");
    }

    @Override
    public RiderDto getMyProfile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMyProfile'");
    }

    @Override
    public List<RideDto> getAllMyRides() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMyRides'");
    }
    
}
