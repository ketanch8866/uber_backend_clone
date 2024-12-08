package com.lirmo.uber.uberApp.entities;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Indexed;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
    @Index(name = "idx_driver_vehicle_id",columnList = "vehicleId"),
    // @Index(name = "available",columnList = "available")
})
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Double rating;
    private Boolean available;
    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point currentLocation;
    private String vehicleId;
}
