package com.lirmo.uber.uberApp.dto;

import java.time.LocalDateTime;
import com.lirmo.uber.uberApp.enums.PaymentMethod;
import com.lirmo.uber.uberApp.enums.RideStatus;

import lombok.Data;

@Data
public class RideDto {
    private Long id;

    private RiderDto rider;

    private DriverDto driver;

    private PaymentMethod paymentMethod;

    private PointDto pickupLocation;

    private PointDto dropOffLocation;

    private LocalDateTime createdTime;

    private RideStatus rideStatus;
    private String otp;
    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
