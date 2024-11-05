package com.lirmo.uber.uberApp.services.impl;

import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.entities.RideRequestEntity;
import com.lirmo.uber.uberApp.enums.RideStatus;
import com.lirmo.uber.uberApp.exceptions.ResourceNotFoundException;
import com.lirmo.uber.uberApp.repositories.RideRepository;
import com.lirmo.uber.uberApp.services.RideService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final ModelMapper modelMapper;

    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride is not found with id: " + rideId ));
    }

    @Override
    public void matchWithDrivers(RideRequestDto rideRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'matchWithDrivers'");
    }

    @Override
    public Ride createNewRide(RideRequestEntity rideRequestEntity, Driver driver) {

        Ride ride = modelMapper.map(rideRequestEntity, Ride.class);
        ride.setDriver(driver);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setOtp(generateOtp());
        ride.setId(null);

        return rideRepository.save(ride);
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

    private String generateOtp() {
        Random random = new Random();
        int otpInt = random.nextInt(100000, 999999);
        return String.format("%06d", otpInt);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }
}
