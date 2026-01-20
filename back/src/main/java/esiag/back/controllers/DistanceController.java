package esiag.back.controllers;

import esiag.back.services.AmbulancePatientDistanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistanceController {

    private final AmbulancePatientDistanceService distanceService;

    public DistanceController(AmbulancePatientDistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @PostMapping("/distances/ambulances-patientA")
    public String calculerDistances() {
        distanceService.calculerDistances();
        return "Distances calculées ";
    }
}
