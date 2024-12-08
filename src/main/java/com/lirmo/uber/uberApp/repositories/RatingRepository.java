package com.lirmo.uber.uberApp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.Rating;
import com.lirmo.uber.uberApp.entities.Ride;
import com.lirmo.uber.uberApp.entities.Rider;
@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {

    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);
    Optional<Rating> findByRide(Ride ride);
} 
