package com.lirmo.uber.uberApp.services;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.RideDto;
import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.dto.RiderDto;
import java.util.List;

public interface RiderService {
    RideRequestDto requestRide(RideRequestDto rideRequestDto);
    RideDto cancelRide(Long rideId);

  
    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    List<RideDto> getAllMyRides();
}
