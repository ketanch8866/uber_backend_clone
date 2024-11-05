package com.lirmo.uber.uberApp.services.impl;

import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.entities.RideRequestEntity;
import com.lirmo.uber.uberApp.exceptions.ResourceNotFoundException;
import com.lirmo.uber.uberApp.repositories.RideRequestRepo;
import com.lirmo.uber.uberApp.services.RideRequestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepo rideRequestRepo;

    @Override
    public RideRequestEntity findRideRequestById(Long rideRequestId) {
        return rideRequestRepo.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride request not found with id " + rideRequestId));
    }

    @Override
    public void updateRideRequest(RideRequestEntity rideRequestEntity) {
        boolean isRideReqestPresent = rideRequestRepo. existsById(rideRequestEntity.getId());
        if (!isRideReqestPresent) {
            throw new ResourceNotFoundException("Ride request not found with id " + rideRequestEntity.getId());
        }
        rideRequestRepo.save(rideRequestEntity);
    }

}
