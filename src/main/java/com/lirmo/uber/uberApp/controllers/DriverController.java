package com.lirmo.uber.uberApp.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.lirmo.uber.uberApp.dto.RideDto;
import com.lirmo.uber.uberApp.dto.RideStartDto;
import com.lirmo.uber.uberApp.services.DriverService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRideRequest(@PathVariable(name = "rideRequestId") Long id) {
        return ResponseEntity.ok(driverService.acceptRide(id));
    }

    @PostMapping("/startRide/{rideId}")
    public ResponseEntity<RideDto> startRide(@PathVariable(name = "rideId") Long id,
            @RequestBody RideStartDto rideStartDto) {
        return ResponseEntity.ok(driverService.startRide(id, rideStartDto.getOtp()));
    }

}
