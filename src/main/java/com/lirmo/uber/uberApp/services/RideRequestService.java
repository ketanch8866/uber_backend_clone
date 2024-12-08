package com.lirmo.uber.uberApp.services;

import com.lirmo.uber.uberApp.entities.RideRequestEntity;

public interface RideRequestService {
    RideRequestEntity findRideRequestById(Long rideRequestId);
    RideRequestEntity createRideRequest(RideRequestEntity rideRequestEntity);
    void updateRideRequest(RideRequestEntity rideRequestEntity);

}
