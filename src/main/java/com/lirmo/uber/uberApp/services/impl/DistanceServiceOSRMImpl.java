package com.lirmo.uber.uberApp.services.impl;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import com.lirmo.uber.uberApp.services.DistanceService;
@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    @Override
    public double calculateDistance(Point src, Point dest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateDistance'");
    }
    
}
