package esiag.back.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import esiag.back.services.AmbulancePatientDistanceService;

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