package com.lirmo.uber.uberApp.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import com.lirmo.uber.uberApp.enums.PaymentMethod;
import com.lirmo.uber.uberApp.enums.RideStatus;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Rider rider;

    @ManyToOne
    private Driver driver;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private PaymentMethod paymentMethod;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point dropOfLocation;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private Double fare;
    private String otp;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
