package com.lirmo.uber.uberApp.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lirmo.uber.uberApp.dto.DriverDto;
import com.lirmo.uber.uberApp.dto.RideDto;
import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.dto.RiderDto;
import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.entities.RideRequestEntity;
import com.lirmo.uber.uberApp.entities.Rider;
import com.lirmo.uber.uberApp.entities.User;
import com.lirmo.uber.uberApp.enums.RideRequestStatus;
import com.lirmo.uber.uberApp.enums.RideStatus;
import com.lirmo.uber.uberApp.exceptions.ResourceNotFoundException;
import com.lirmo.uber.uberApp.repositories.RiderRepository;
import com.lirmo.uber.uberApp.services.DriverService;
import com.lirmo.uber.uberApp.services.RatingService;
import com.lirmo.uber.uberApp.services.RideRequestService;
import com.lirmo.uber.uberApp.services.RideService;
import com.lirmo.uber.uberApp.services.RiderService;
import com.lirmo.uber.uberApp.strategies.manager.StrategyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final StrategyManager strategyManager;
    private final RideService rideService;
    private final RideRequestService rideRequestService;
    private final RiderRepository riderRepository;
    private final DriverService driverService;
    private final RatingService ratingService;
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

        RideRequestEntity savedRideRequest = rideRequestService.createRideRequest(rideRequest);
        List<Driver> drivers = strategyManager.driverMatchingStrategy(rider.getRating())
                .findMatchingDrivers(rideRequest);
        // log.info("requestRide=== >savedRideRequest " + savedRideRequest);
        // TODO Notify all drivers for this ride request
        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    @Transactional
    public RideDto cancelRide(Long rideId) {
        Rider currentRider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);
        if (!ride.getRider().equals(currentRider)) {
            throw new RuntimeException("Rider does not owned this ride with id: " + rideId);
        }
        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride can not be cancelled, invalid status: " + ride.getRideStatus());
        }
        Driver driver = ride.getDriver();

        driverService.updateDriverAvailability(driver, true);
        RideRequestEntity rideRequestEntity = ride.getRideRequestEntity();
        rideRequestEntity.setRideRequestStatus(RideRequestStatus.CANCELLED);
        rideRequestService.updateRideRequest(rideRequestEntity);
        return modelMapper.map(rideService.updateRideStatus(ride, RideStatus.CANCELLED), RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();
        if (!rider.equals(ride.getRider())) {
            throw new RuntimeException("Rider is not owner of this Ride");
        }
        if (!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException(
                    "Ride status is not ENDED hence can not start rating, status: " + ride.getRideStatus());
        }
        Driver savedDriver = ratingService.rateDriver(ride, rating);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    public RiderDto getMyProfile() {
        Rider rider = getCurrentRider();
        return modelMapper.map(rider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider currentRider = getCurrentRider();
        return rideService.getAllRidesOfRider(currentRider, pageRequest)
                .map((ride) -> modelMapper.map(ride, RideDto.class));
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider.builder().user(user).rating(0.0).build();
        return riderRepository.save(rider);  
    }

    @Override
    public Rider getCurrentRider() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return riderRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException("Rider not associated with user with id: " + user.getId()));

    }

}
