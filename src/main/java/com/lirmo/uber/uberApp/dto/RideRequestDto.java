package com.lirmo.uber.uberApp.dto;

import org.locationtech.jts.geom.Point;
import com.lirmo.uber.uberApp.enums.PaymentMethod;
import com.lirmo.uber.uberApp.enums.RideRequestStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {
    private Long id;

    private RiderDto rider;

    private Point pickupLocation;

    private Point dropOfLocation;

    private LocalDateTime requestedTime;

    private RideRequestStatus rideRequestStatus;

    private PaymentMethod paymentMethod;
}
