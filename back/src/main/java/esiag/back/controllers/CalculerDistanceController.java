package esiag.back.controllers;

import esiag.back.services.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/distance")
public class CalculerDistanceController {

    @Autowired
    private DistanceService distanceService;

    @GetMapping
    public ResponseEntity<Double> getDistance(
            @RequestParam double lat1,
            @RequestParam double lon1,
            @RequestParam double lat2,
            @RequestParam double lon2) {

        double distance = distanceService.calculerdistance(lat1, lon1, lat2, lon2);
        return ResponseEntity.ok(distance);
    }
}
