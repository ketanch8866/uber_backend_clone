package com.lirmo.uber.uberApp.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.entities.RideRequestEntity;
import com.lirmo.uber.uberApp.entities.Rider;
import com.lirmo.uber.uberApp.enums.RideStatus;

public interface RideService {
    Ride getRideById(Long rideId);

    void matchWithDrivers(RideRequestDto rideRequestDto);

    Ride createNewRide(RideRequestEntity rideRequestEntity, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);
}
