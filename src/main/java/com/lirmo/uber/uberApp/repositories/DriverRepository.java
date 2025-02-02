package com.lirmo.uber.uberApp.repositories;

import java.util.List;
import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lirmo.uber.uberApp.entities.Driver;
import com.lirmo.uber.uberApp.entities.User;

//ST_Distance(point1,point2)
//ST_DWithin(point1,point2)
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

        @Query(value = "SELECT d.*, ST_Distance(d.current_location, :pickupLocation) AS distance " +
                        "FROM driver d " +
                        "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 10000) " +
                        "ORDER BY distance " +
                        "LIMIT 10", nativeQuery = true)
        List<Driver> findTenNearestDrivers(Point pickupLocation);

        @Query(value = "SELECT d.* FROM driver d " +
                        "WHERE d.available = true AND ST_DWithin(d.current_location, :pickupLocation, 15000) " +
                        "ORDER BY d.rating DESC " +
                        "LIMIT 10", nativeQuery = true)
        List<Driver> findTopRatedTenNearByDrivers(Point pickupLocation);

        Optional<Driver> findByUser(User user);

}
