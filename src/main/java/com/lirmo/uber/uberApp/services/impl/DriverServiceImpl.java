package com.lirmo.uber.uberApp.services.impl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.RideDto;
import com.lirmo.uber.uberApp.dto.RiderDto;
import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.entities.RideRequestEntity;
import com.lirmo.uber.uberApp.entities.Rider;
import com.lirmo.uber.uberApp.entities.User;
import com.lirmo.uber.uberApp.enums.RideRequestStatus;
import com.lirmo.uber.uberApp.enums.RideStatus;
import com.lirmo.uber.uberApp.exceptions.DriverNotAvailableException;
import com.lirmo.uber.uberApp.exceptions.ResourceNotFoundException;
import com.lirmo.uber.uberApp.exceptions.RideRequestCanNotAcceptedException;
import com.lirmo.uber.uberApp.repositories.DriverRepository;
import com.lirmo.uber.uberApp.services.DriverService;
import com.lirmo.uber.uberApp.services.PaymentService;
import com.lirmo.uber.uberApp.services.RatingService;
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
    private final PaymentService paymentService;
    private final RatingService ratingService;

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
        Driver savedDriver = updateDriverAvailability(currentDriver, false);
        Ride ride = rideService.createNewRide(rideRequestEntity, savedDriver);
        rideRequestService.updateRideRequest(rideRequestEntity);
        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    @Transactional
    public RideDto cancelRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver currentDriver = getCurrentDriver();
        if (!currentDriver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver can not cancel a ride as he has not accepted it earlier");
        }
        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride can not be cancelled, invalid status: " + ride.getRideStatus());
        }
        updateDriverAvailability(currentDriver, true);
        RideRequestEntity rideRequestEntity = ride.getRideRequestEntity();
        rideRequestEntity.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequestService.updateRideRequest(rideRequestEntity);
        return modelMapper.map(rideService.updateRideStatus(ride, RideStatus.CANCELLED), RideDto.class);
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
        ratingService.createNewRating(savedRide);
        paymentService.createNewPayment(savedRide);
        return modelMapper.map(savedRide, RideDto.class);

    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver can not end a ride as he has not accepted it earlier");
        }
        if (!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException(
                    "Ride status is not ONGOING hence can not be ended, status: " + ride.getRideStatus());
        }
        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver, true);
        paymentService.processPayment(savedRide);
        return modelMapper.map(savedRide, RideDto.class);

    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();
        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver is not owner of this Ride");
        }
        if (!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException(
                    "Ride status is not ENDED hence can not start rating, status: " + ride.getRideStatus());
        }
        Rider savedRider = ratingService.rateRider(ride, rating);
        return modelMapper.map(savedRider, RiderDto.class);
    }

    @Override
    public DriverDto getMyProfile() {
        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver, pageRequest)
                .map(ride -> modelMapper.map(ride, RideDto.class));
    }

    @Override
    public Driver getCurrentDriver() {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return driverRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Current Driver not associated with user with id: "+user.getId()));
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {

        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(User user, String vehicalId) {
        Driver driver = Driver.builder()
                .user(user)
                .available(true)
                .rating(0.0)
                .vehicleId(vehicalId)
                .build();

        return driverRepository.save(driver);
    }

}
