package com.lirmo.uber.uberApp.services;

import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.entities.Rider;

public interface RatingService {
    Driver rateDriver(Ride ride, Integer rating);

    Rider rateRider(Ride ride,  Integer rating);

    void createNewRating(Ride ride);
}
