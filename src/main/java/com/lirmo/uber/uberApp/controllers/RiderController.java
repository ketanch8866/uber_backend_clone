package com.lirmo.uber.uberApp.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lirmo.uber.uberApp.dto.RideRequestDto;
import com.lirmo.uber.uberApp.services.RiderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/rider")
public class RiderController {

    private final RiderService riderService;

    public RiderController(RiderService riderservice) {
        this.riderService = riderservice;
    }

    @PostMapping("/requestRide")
    public ResponseEntity<RideRequestDto> requestRide(@RequestBody RideRequestDto rideRequestDto) {

        return ResponseEntity.ok(riderService.requestRide(rideRequestDto));
    }
}
