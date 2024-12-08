package com.lirmo.uber.uberApp.services;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.RideDto;
import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.dto.RiderDto;
import com.lirmo.uber.uberApp.entities.Rider;
import com.lirmo.uber.uberApp.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RiderService {
    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    Rider createNewRider(User user);

    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Rider getCurrentRider();
}
