package com.lirmo.uber.uberApp.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.RideDto;
import com.lirmo.uber.uberApp.dto.RiderDto;
import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.entities.RideRequestEntity;
import com.lirmo.uber.uberApp.enums.RideRequestStatus;
import com.lirmo.uber.uberApp.enums.RideStatus;
import com.lirmo.uber.uberApp.exceptions.DriverNotAvailableException;
import com.lirmo.uber.uberApp.exceptions.ResourceNotFoundException;
import com.lirmo.uber.uberApp.exceptions.RideRequestCanNotAcceptedException;
import com.lirmo.uber.uberApp.repositories.DriverRepository;
import com.lirmo.uber.uberApp.services.DriverService;
import com.lirmo.uber.uberApp.services.RideRequestService;
import com.lirmo.uber.uberApp.services.RideService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;
    private final RideService rideService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        RideRequestEntity rideRequestEntity = rideRequestService.findRideRequestById(rideRequestId);
        if (!rideRequestEntity.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RideRequestCanNotAcceptedException(
                    "RideRequest can not be accepted, status is " + rideRequestEntity.getRideRequestStatus());
        }
        Driver currentDriver = getCurrentDriver();
        if (!currentDriver.getAvailable()) {
            throw new DriverNotAvailableException("Driver cannot acceprt ride due to unavailability");
        }

        rideRequestEntity.setRideRequestStatus(RideRequestStatus.CONFIRMED);
        currentDriver.setAvailable(false);
        Driver savedDriver = driverRepository.save(currentDriver);
        Ride ride = rideService.createNewRide(rideRequestEntity, savedDriver);
        rideRequestService.updateRideRequest(rideRequestEntity);
        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelRide'");
    }

    @Override
    @Transactional
    public RideDto startRide(Long rideId, String otp) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver can not start a ride as he has not accepted it earlier");
        }
        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException(
                    "Ride status is not CONFIRMED hence can not be started, status: " + ride.getRideStatus());
        }
        if (!otp.equals(ride.getOtp())) {
            throw new RuntimeException("OTP is not valid, otp: " + otp);

        }
        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);

        return modelMapper.map(savedRide, RideDto.class);

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

    @Override
    public Driver getCurrentDriver() {
        return driverRepository.findById(2L)
                .orElseThrow(() -> new ResourceNotFoundException("Current Driver is not found"));
    }

}
