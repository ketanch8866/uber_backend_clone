package com.lirmo.uber.uberApp.dto;

import org.locationtech.jts.geom.Point;
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

    private Point pickupLocation;

    private Point dropOfLocation;

    private LocalDateTime createdTime;

    private RideStatus rideStatus;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
