package com.lirmo.uber.uberApp.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.RideDto;
import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.dto.RiderDto;
import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.RideRequestEntity;
import com.lirmo.uber.uberApp.entities.Rider;
import com.lirmo.uber.uberApp.entities.User;
import com.lirmo.uber.uberApp.enums.RideRequestStatus;
import com.lirmo.uber.uberApp.exceptions.ResourceNotFoundException;
import com.lirmo.uber.uberApp.repositories.RideRequestRepo;
import com.lirmo.uber.uberApp.repositories.RiderRepository;
import com.lirmo.uber.uberApp.services.RiderService;
import com.lirmo.uber.uberApp.strategies.manager.StrategyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final StrategyManager strategyManager;

    private final RideRequestRepo rideRequestRepo;
    private final RiderRepository riderRepository;
    // Logger log = LoggerFactory.getLogger(RiderServiceImpl.class);

    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();
        RideRequestEntity rideRequest = modelMapper.map(rideRequestDto, RideRequestEntity.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        Double fare = strategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);
        rideRequest.setRider(rider);
        // log.info("requestRide=== > " + rideRequest);

        RideRequestEntity savedRideRequest = rideRequestRepo.save(rideRequest);
        List<Driver> drivers = strategyManager.driverMatchingStrategy(rider.getRating())
                .findMatchingDrivers(rideRequest);
        // log.info("requestRide=== >savedRideRequest " + savedRideRequest);
        // TODO Notify all drivers for this ride request
        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelRide'");
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rateDriver'");
    }

    @Override
    public RiderDto getMyProfile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMyProfile'");
    }

    @Override
    public List<RideDto> getAllMyRides() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllMyRides'");
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider.builder().user(user).rating(0.0).build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
        // TODO implements Spring security
        return riderRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Rider is not found"));

    }

}
