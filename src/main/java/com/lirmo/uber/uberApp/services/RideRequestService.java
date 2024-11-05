package com.lirmo.uber.uberApp.services;

import com.lirmo.uber.uberApp.entities.RideRequestEntity;

public interface RideRequestService {
    RideRequestEntity findRideRequestById(Long rideRequestId);
    void updateRideRequest(RideRequestEntity rideRequestEntity);
}
