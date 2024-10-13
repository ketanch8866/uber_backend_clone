package com.lirmo.uber.uberApp.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.enums.RideStatus;
import com.lirmo.uber.uberApp.services.RideService;
@Service
public class RideServiceImpl implements RideService {

    @Override
    public Ride getRideById(Long rideId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRideById'");
    }

    @Override
    public void matchWithDrivers(RideRequestDto rideRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'matchWithDrivers'");
    }

    @Override
    public Ride createNewRide(RideRequestDto rideRequestDto, Driver driver) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createNewRide'");
    }

    @Override
    public Ride updateRideStatus(Long rideId, RideStatus rideStatus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRideStatus'");
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Long riderId, PageRequest pageRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRidesOfRider'");
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Long driverId, PageRequest pageRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRidesOfDriver'");
    }

}
