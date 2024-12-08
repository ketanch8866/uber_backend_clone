package com.lirmo.uber.uberApp.services.impl;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.lirmo.uber.uberApp.services.DistanceService;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DistanceServiceOSMRImpl implements DistanceService {
    final RestClient restClient;

    @Override
    public double calculateDistance(Point src, Point dest) {
        try {
            String uri = src.getX() + "," + src.getY() + ";" + dest.getX() + "," + dest.getY();
            OSRMResponseDto osrmResponseDto = restClient.get()
                    .uri(uri).retrieve().onStatus(HttpStatusCode::isError, (req, res) -> {
                        throw new RuntimeException("Server Error occured");
                    })
                    .body(OSRMResponseDto.class);
            return (osrmResponseDto.getRoutes().get(0).getDistance()) / 1000;
        } catch (Exception e) {
            throw new RuntimeException("Error while getting data from OSRM " + e.getMessage());
        }
    }

}

@Data
class OSRMResponseDto {

    private List<OSRMRoute> routes;
}

@Data
class OSRMRoute {
    @NotNull
    private Double distance;
}