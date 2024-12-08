package com.lirmo.uber.uberApp.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.Rating;
import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.entities.Rider;
import com.lirmo.uber.uberApp.exceptions.ResourceNotFoundException;
import com.lirmo.uber.uberApp.exceptions.RuntimeConflictException;
import com.lirmo.uber.uberApp.repositories.DriverRepository;
import com.lirmo.uber.uberApp.repositories.RatingRepository;
import com.lirmo.uber.uberApp.repositories.RiderRepository;
import com.lirmo.uber.uberApp.services.DriverService;
import com.lirmo.uber.uberApp.services.RatingService;
import com.lirmo.uber.uberApp.services.RiderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;

    @Override
    @Transactional
    public Driver rateDriver(Ride ride, Integer rating) {
        Driver driver = ride.getDriver();
        Rating ratingObj = getRatingOfRide(ride);
        if (ratingObj.getDriverRating() != null)
            throw new RuntimeConflictException("Driver has already been rated, can not rate again");
        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);
        Double newDriverRating = ratingRepository.findByDriver(driver).stream().mapToDouble(Rating::getDriverRating)
                .average().orElse(0.0);
        driver.setRating(newDriverRating);
        return driverRepository.save(driver);
    }

    @Override
    @Transactional
    public Rider rateRider(Ride ride, Integer rating) {
        Rider rider = ride.getRider();
        Rating ratingObj = getRatingOfRide(ride);
        if (ratingObj.getRiderRating() != null)
            throw new RuntimeConflictException("Rider has already been rated, can not rate again");
        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);
        Double newRiderRating = ratingRepository.findByRider(rider)
                .stream().mapToDouble(Rating::getRiderRating).average().orElse(0);

        rider.setRating(newRiderRating);
        return riderRepository.save(rider);

    }

    private Rating getRatingOfRide(Ride ride) {
        return ratingRepository.findByRide(ride).orElseThrow(
                () -> new ResourceNotFoundException("Rating is not found for the ride with id: " + ride.getId()));
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder().driver(ride.getDriver())
                .ride(ride).rider(ride.getRider())
                .build();
        ratingRepository.save(rating);
    }

}
